package com.zq.redis.lock.locker;

import com.zq.redis.RedisSingleImpl;
import redis.clients.jedis.Jedis;

/**
 * @author zhangqing
 * @Package com.zq.redis.lock.locker
 * @date 2020/7/25 15:09
 */
public class RedisSignleLocker extends AbstractRedisLocker<Jedis> {

    private RedisSingleImpl redisSingleImpl;

    private long timeOut;

    public RedisSignleLocker(RedisSingleImpl redisSingleImpl){
        this.redisSingleImpl = redisSingleImpl;
    }

    @Override
    protected Jedis getRedis() {
        return redisSingleImpl.getResource();
    }

    @Override
    protected void returnRedis(Jedis jedis) {
        jedis.close();
    }
}
