package com.zq.redis.lock.locker;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author zhangqing
 * @Package com.zq.redis.lock.locker
 * @date 2020/7/25 15:06
 */
public class RedisLocker extends AbstractRedisLocker<Jedis> {

    private JedisPool jedisPool;

    private long timeOut;

    public RedisLocker(JedisPool jedisPool, long timeOut){
        this.jedisPool = jedisPool;
        this.timeOut = timeOut;
    }

    @Override
    protected Jedis getRedis() {
        return jedisPool.getResource();
    }

    @Override
    protected void returnRedis(Jedis jedis) {
        jedis.close();
    }
}
