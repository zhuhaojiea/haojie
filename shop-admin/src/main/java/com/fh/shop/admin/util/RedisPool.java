package com.fh.shop.admin.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    private static JedisPool jedisPool;

    private RedisPool() {

    }

    private static void initPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMinIdle(500);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.253.133", 7020);
    }

    // 加载类的时候执行静态块中的代码，而且只会 执行一次
    static {
        initPool();
    }

    public static Jedis getResource() {
        return jedisPool.getResource();
    }


}
