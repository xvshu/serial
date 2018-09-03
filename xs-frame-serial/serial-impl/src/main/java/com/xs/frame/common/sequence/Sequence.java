package com.xs.frame.common.sequence;

/**
 * Created by Administrator on 2015/4/22.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Sequence {
    private static final Log log = LogFactory.getLog(Sequence.class);

    private int blockSize = 5;
    private long startValue = 0L;
    private static final String GET_SQL = "select id from sequence_value where name = ?";
    private static final String NEW_SQL = "insert into sequence_value (id,name) values (?,?)";
    private static final String UPDATE_SQL = "update sequence_value set id = ?  where name = ? and id = ?";

    private static final String UPDATE_SQL_Redis = "update sequence_value set id = ?  where name = ? and id <= ?";

    private Map<String, Step> stepMap = new HashMap();
    private DataSource dataSource;
    private RedisSequence redisSequence;

    private boolean getNextBlock(String sequenceName, Step step) {
        Long value =0L;
        Long value_db = getPersistenceValue(sequenceName);
        value=value_db;
        Long redisNo = redisSequence.getMaxValue(sequenceName);
        if (value == null) {
            try {
                Long initNo = null;
                if(redisNo!=null && redisNo>0){
                    log.warn("=getNextBlock=>newPersistenceValue but redisNo is not empty " +
                            "sequenceName:"+String.valueOf(sequenceName)+" redisNo: "+String.valueOf(redisNo));
                    initNo=redisNo;
                }
                value = newPersistenceValue(sequenceName,initNo);
                value_db=value;
                redisNo = redisSequence.getMaxValue(sequenceName);
            } catch (Exception e) {
                log.error("newPersistenceValue error!");
                value = getPersistenceValue(sequenceName);
                value_db=value;
                redisNo = redisSequence.getMaxValue(sequenceName);
            }
        }
        if(redisNo==null){
            log.warn("=getNextBlock=>redisNo is null so make redis " +
                    "sequenceName:"+String.valueOf(sequenceName)+" value:"+String.valueOf(value + this.blockSize));
            redisSequence.updateMaxValue(sequenceName,value + this.blockSize);

        }else if(redisNo>(value)){
            log.warn("=getNextBlock=>redisNo>value+blockSize so make value=redisNo " +
                    "redisNo:"+String.valueOf(redisNo)+" value: "+String.valueOf(value));
            value=redisNo;
        }else if(redisNo<(value)){
            log.warn("=getNextBlock=>redisNo>value+blockSize so make value=redisNo " +
                    "redisNo:"+String.valueOf(redisNo)+" value: "+String.valueOf(value));
            redisSequence.updateMaxValue(sequenceName,value + this.blockSize);
        }
        boolean b = saveValue(value.longValue(),value_db ,sequenceName) == 1;
        if (b) {
            step.setCurrentValue(value.longValue());
            step.setEndValue(value.longValue() + this.blockSize);
        }
        return b;
    }

    public synchronized long get(String sequenceName) {
        Step step = (Step) this.stepMap.get(sequenceName);
        if (step == null) {
            step = new Step(this.startValue, this.startValue + this.blockSize);
            this.stepMap.put(sequenceName, step);
        } else if (step.currentValue < step.endValue) {
            return step.incrementAndGet();
        }

        for (int i = 0; i < this.blockSize; i++) {
            if (getNextBlock(sequenceName, step)) {
                return step.incrementAndGet();
            }
        }
        throw new RuntimeException("No more value.");
    }

    private int saveValue(long value,long db_value, String sequenceName) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement("update sequence_value set id = ?  where name = ? and id = ?");
            statement.setLong(1, value + this.blockSize);
            statement.setString(2, sequenceName);
            statement.setLong(3, db_value);
            int result =  statement.executeUpdate();

            log.info("=getNextBlock=>saveValue " +
                    "update sequence_value set id = "+String.valueOf(value + this.blockSize)+"  where name = "+sequenceName+" and id = "+db_value +" result:"+result);
            log.info("=getNextBlock=>saveValue " +
                    "sequenceName:"+String.valueOf(sequenceName)+" value: "+String.valueOf(value + this.blockSize));
            redisSequence.updateMaxValue(sequenceName,value + this.blockSize);
            return result;
        } catch (Exception e) {
            log.error("newPersistenceValue error!", e);
            throw new RuntimeException("newPersistenceValue error!", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("close statement error!", e);
                }
            }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("close connection error!", e);
                }
        }
    }

    private Long getPersistenceValue(String sequenceName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement("select id from sequence_value where name = ?");
            statement.setString(1, sequenceName);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return Long.valueOf(resultSet.getLong("id"));
        } catch (Exception e) {
            this.stepMap.remove(sequenceName);
            log.error("getPersistenceValue error!", e);
            throw new RuntimeException("getPersistenceValue error!", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("close resultset error!", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("close statement error!", e);
                }
            }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("close connection error!", e);
                }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("close resultset error!", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("close statement error!", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("close connection error!", e);
            }
        }

        return null;
    }

    private Long newPersistenceValue(String sequenceName,Long initNo) {
        if(initNo!=null){

        }
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement("insert into sequence_value (id,name) values (?,?)");
            statement.setLong(1, initNo==null?this.startValue:initNo);
            statement.setString(2, sequenceName);
            statement.executeUpdate();
            if(initNo!=null){
                redisSequence.updateMaxValue(sequenceName,this.startValue+this.blockSize);
                log.warn("=getNextBlock=>redis.updateMaxValue initNo is not empty " +
                        "sequenceName:"+String.valueOf(sequenceName)+" initNo: "+String.valueOf(initNo));
            }
        } catch (Exception e) {
            log.error("newPersistenceValue error!", e);
            throw new RuntimeException("newPersistenceValue error!", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("close statement error!", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("close connection error!", e);
                }
            }
        }
        return Long.valueOf(this.startValue);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public void setStartValue(long startValue) {
        this.startValue = startValue;
    }

    public void setRedisSequence(RedisSequence redisSequence) {
        this.redisSequence = redisSequence;
    }

    static class Step {
        private long currentValue;
        private long endValue;

        Step(long currentValue, long endValue) {
            this.currentValue = currentValue;
            this.endValue = endValue;
        }

        public void setCurrentValue(long currentValue) {
            this.currentValue = currentValue;
        }

        public void setEndValue(long endValue) {
            this.endValue = endValue;
        }

        public long incrementAndGet() {
            return ++this.currentValue;
        }
    }
}