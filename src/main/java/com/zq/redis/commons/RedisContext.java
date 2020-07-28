package com.zq.redis.commons;

import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisContext {

    public static final Map<String, JedisPubSub> TOPIC_SUB_MAP = new ConcurrentHashMap<>();

    public static final Map<String, JedisPubSub> TOPIC_PSUB_MAP = new ConcurrentHashMap<>();


}
