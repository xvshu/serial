package com.xs.frame.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    public Jedis getInstance(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}