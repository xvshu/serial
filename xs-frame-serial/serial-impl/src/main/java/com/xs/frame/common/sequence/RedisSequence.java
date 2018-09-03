package com.xs.frame.common.sequence;

import com.xs.frame.common.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Service
public class RedisSequence {
    private String RedisKey="frame-xs-serial-";

    @Autowired
    RedisService redisService;

    public void updateMaxValue(String sequenceName, long value){
        Jedis jedis=null;
        try{
            jedis=redisService.getInstance();
            jedis.set(RedisKey+sequenceName,String.valueOf(value));
            jedis.expire(RedisKey+sequenceName, 2*24*60*60);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }

    }

    public Long getMaxValue(String sequenceName){
        Jedis jedis=null;
        String result=null;
        try {
            jedis=redisService.getInstance();
            result = jedis.get(RedisKey + sequenceName);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return result == null ? null : Long.valueOf(result);
    }

}
