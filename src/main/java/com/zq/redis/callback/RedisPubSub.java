package com.zq.redis.callback;

import com.zq.redis.commons.RedisContext;
import redis.clients.jedis.JedisPubSub;

/**
 * @author zhangqing
 * @Package com.zq.redis.callback
 * @date 2020/7/28 16:27
 */
public class RedisPubSub extends JedisPubSub {

    @Override
    public void unsubscribe(String... channels) {
        super.unsubscribe(channels);
        if (channels != null) {
            for (String channel : channels) {
                RedisContext.TOPIC_SUB_MAP.remove(channel);
            }
        }
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        RedisContext.TOPIC_SUB_MAP.clear();
    }

    @Override
    public void punsubscribe(String... patterns) {
        super.punsubscribe(patterns);
        if (patterns != null) {
            for (String pattern : patterns) {
                RedisContext.TOPIC_PSUB_MAP.remove(pattern);
            }
        }
    }

    @Override
    public void punsubscribe() {
        super.punsubscribe();
        RedisContext.TOPIC_PSUB_MAP.clear();
    }
}

