package com.xs.frame.common.sequence;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xs.frame.common.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/5/9.
 */
@Service
public class SequenceHelp {

    private static Integer blockSizeL1=20000;
    private static Integer blockSizeL2=50000;

    @Autowired
    private   DataSource dataSource;

    public  DataSource getDataSource() {
        return dataSource;
    }

    public  RedisSequence getRedisSequence() {
        return redisSequence;
    }

    @Autowired
    private  RedisSequence redisSequence;


    private final SequenceUtil sequenceUtil = new SequenceUtil();

    public long get(String name) {
        long sequeceNum;
        try {
            sequeceNum = sequenceUtil.get(name);
        }catch (Exception e){
            e.printStackTrace();
            if(getDataSource()==null){
                throw new RuntimeException("getDataSource is null");
            }
            sequenceUtil.addSequence(name, new Sequence() {{
                setDataSource(getDataSource());
                setRedisSequence(getRedisSequence());
                setBlockSize(blockSizeL2);
            }});
            sequeceNum = sequenceUtil.get(name);
        }
        return sequeceNum;
    }

    public  List<Long> get(String name,Integer num) {
        List<Long> list = new ArrayList<Long>();
        try {
            if (StringUtils.isNotEmpty(name) && num != null && num.intValue() > 0) {
                for (int i = 1; i <= num; i++) {
                    list.add(sequenceUtil.get(name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            if(getDataSource()==null){
                throw new RuntimeException("getDataSource is null");
            }

            sequenceUtil. addSequence(name, new Sequence() {{
                setDataSource(getDataSource());
                setRedisSequence(getRedisSequence());
                setBlockSize(blockSizeL2);
            }});

            if (StringUtils.isNotEmpty(name) && num != null && num.intValue() > 0) {
                for (int i = 1; i <= num; i++) {
                    list.add(sequenceUtil.get(name));
                }
            }

        }
        return list;
    }
}
