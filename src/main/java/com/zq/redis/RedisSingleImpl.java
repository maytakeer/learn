package com.zq.redis;

import com.zq.redis.callback.RedisPubSub;
import com.zq.redis.commons.RedisContext;
import com.zq.redis.commons.ThreadPoolManager;
import com.zq.redis.serializer.SerializerFactory;
import com.zq.redis.util.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.util.SafeEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangqing
 * @Package com.zq.redis
 * @date 2020/7/25 15:21
 */
public class RedisSingleImpl implements Redis {

    transient Logger logger = LoggerFactory.getLogger(RedisSingleImpl.class);

    private JedisPool jedisPool;


    public RedisSingleImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    @Override
    public Boolean exists(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.exists(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis exists", e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    @Override
    public Long persist(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.persist(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis persist", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public String type(String key) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }


        Jedis jedis = null;
        try {
            jedis = getResource();

            return jedis.type(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis type", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long expire(String key, int seconds) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.expire(SafeEncoder.encode(key), seconds);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis expire", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long expire(Map<String, Integer> map) {
        if (EmptyUtils.isEmpty(map)) {
            logger.error("Map can not be empty!");
            return 0L;
        }


        Long sum = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                sum += this.expire(entry.getKey(), entry.getValue());
            }
            return sum;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis expire(map)", e);
        } finally {
            closeJedis(jedis);
        }
        return sum;
    }

    @Override
    public Long expireAt(String key, Long unixTime) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return 0L;
        }


        Jedis jedis = null;
        try {
            jedis = getResource();

            return jedis.expireAt(SafeEncoder.encode(key), unixTime);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis expireAt", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long expireAt(Map<String, Long> map) {
        if (EmptyUtils.isEmpty(map)) {
            logger.error("Map can not be empty!");
            return 0L;
        }


        Long sum = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                sum += this.expireAt(entry.getKey(), entry.getValue());
            }
            return sum;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis expireAt(map)", e);
        } finally {
            closeJedis(jedis);
        }
        return sum;
    }

    @Override
    public Long ttl(String key) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return 0L;
        }


        Jedis jedis = null;
        try {
            jedis = getResource();

            return jedis.ttl(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis ttl", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long del(String key) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return 0L;
        }


        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.del(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis del", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long del(String... keys) {
        if (EmptyUtils.isEmpty(keys)) {
            logger.error("Keys can not be empty!");
            return 0L;
        }


        Long sum = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            for (String key : keys) {
                sum += this.del(key);
            }
            return sum;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis del[]", e);
        } finally {
            closeJedis(jedis);
        }
        return sum;
    }

    @Override
    public String setString(String key, final String value) {
        return this.setString(key, -1, value);
    }

    @Override
    public String setString(String key, final int seconds, final String value) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return "failed";
        }
        if (EmptyUtils.isEmpty(value)) {
            //logger.info("Value can not be empty!");
            return "failed";
        }


        Jedis jedis = null;
        try {
            jedis = getResource();

            if (seconds > 0) {
                return jedis.setex(key, seconds, value);
            } else {
                return jedis.set(key, value);
            }
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis rpush", e);
        } finally {
            closeJedis(jedis);
        }
        return "failed";
    }

    @Override
    public String getString(String key) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return "failed";
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.get(key);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis getString", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public String getSetString(String key, final String value) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }
        if (EmptyUtils.isEmpty(value)) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.getSet(key, value);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis rpush", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public String setObject(String key, final Object value) {
        return this.setObject(key, -1, value);
    }

    @Override
    public String setObject(String key, int seconds, Object value) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return "failed";
        }
        if (EmptyUtils.isEmpty(value)) {
            return "failed";
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (seconds > 0) {
                return jedis.setex(SafeEncoder.encode(key), seconds, serialize(value));
            } else {
                return jedis.set(SafeEncoder.encode(key), serialize(value));
            }
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis setObject", e);
        } finally {
            closeJedis(jedis);
        }
        return "failed";
    }

    @Override
    public Object getObject(String key) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return deserialize(jedis.get(SafeEncoder.encode(key)));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis getObject", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> T getObject(String key, Class<T> returnType) {
        Object obj = getObject(key);
        return obj == null ? null : returnType.cast(obj);
    }

    @Override
    public Object getSetObject(String key, Object value) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }
        if (EmptyUtils.isEmpty(value)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.getSet(SafeEncoder.encode(key), serialize(value));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis getSetObject", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * 缓存新的对象,并返回旧的对象.
     *
     * @param key
     * @param value
     * @param returnType
     * @return
     */
    @Override
    public <T> T getSetObject(String key, Object value, Class<T> returnType) {
        Object result = getSetObject(key, value);
        return result == null ? null : returnType.cast(result);
    }

    @Override
    public Long incr(String key) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.incr(key);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis incr", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long incrBy(String key, long integer) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.incrBy(key, integer);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis incrBy", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long decr(String key) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.decr(key);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis decr", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long decrBy(String key, long integer) {
        if (EmptyUtils.isEmpty(key)) {
            logger.error("Key can not be empty!");
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.decrBy(key, integer);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis decrBy", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long hset(String key, final String field, final Object value) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(field) || EmptyUtils.isEmpty(value)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field), serialize(value));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hset", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Boolean hmset(String key, final Map<String, Object> map) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(map)) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();

            Map<byte[], byte[]> hash = new HashMap<byte[], byte[]>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                hash.put(SafeEncoder.encode(entry.getKey()), serialize(entry.getValue()));
            }
            jedis.hmset(SafeEncoder.encode(key), hash);
            return true;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hmset", e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    @Override
    public Object hget(String key, final String field) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(field)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return deserialize(jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field)));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hget", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * 返回名称为key的hash中field对应的value.
     *
     * @param key
     * @param field
     * @param returnType
     * @return
     */
    @Override
    public <T> T hget(String key, String field, Class<T> returnType) {
        Object object = hget(key, field);
        return object == null ? null : returnType.cast(object);
    }

    @Override
    public Map<String, Object> hgetAll(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Map<byte[], byte[]> bMap = jedis.hgetAll(SafeEncoder.encode(key));
            if (EmptyUtils.isEmpty(bMap)) {
                return null;
            }
            Map<String, Object> map = new HashMap<String, Object>(bMap.size());
            for (Map.Entry<byte[], byte[]> entry : bMap.entrySet()) {
                map.put(SafeEncoder.encode(entry.getKey()), deserialize(entry.getValue()));
            }
            return map;
        } catch (JedisConnectionException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hgetAll", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Map<String, T> hgetAll(String key, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Map<byte[], byte[]> bMap = jedis.hgetAll(SafeEncoder.encode(key));
            if (EmptyUtils.isEmpty(bMap)) {
                return null;
            }
            Map<String, T> map = new HashMap<String, T>(bMap.size());
            for (Map.Entry<byte[], byte[]> entry : bMap.entrySet()) {
                map.put(SafeEncoder.encode(entry.getKey()), deserialize(entry.getValue(), returnType));
            }
            return map;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hgetAll", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public List<Object> hmget(String key, final String... fields) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(fields)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bfields = new byte[fields.length][];
            for (int i = 0; i < bfields.length; i++) {
                bfields[i] = SafeEncoder.encode(fields[i]);
            }
            List<byte[]> bList = jedis.hmget(SafeEncoder.encode(key), bfields);
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<Object> list = new ArrayList<Object>(fields.length);
            for (byte[] bts : bList) {
                list.add(deserialize(bts));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hmget", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> List<T> hmget(String key, Class<T> returnType, String... fields) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(fields)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bfields = new byte[fields.length][];
            for (int i = 0; i < bfields.length; i++) {
                bfields[i] = SafeEncoder.encode(fields[i]);
            }
            List<byte[]> bList = jedis.hmget(SafeEncoder.encode(key), bfields);
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<T> list = new ArrayList<>(fields.length);
            for (byte[] bts : bList) {
                list.add(deserialize(bts, returnType));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hmget", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Boolean hexists(String key, final String field) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(field)) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hexists(SafeEncoder.encode(key), SafeEncoder.encode(field));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hexists", e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    @Override
    public Long hdel(String key, String... fields) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(fields)) {
            return 0L;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bfields = new byte[fields.length][];
            for (int i = 0; i < bfields.length; i++) {
                bfields[i] = SafeEncoder.encode(fields[i]);
            }
            return jedis.hdel(SafeEncoder.encode(key), bfields);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hdel", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long hlen(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hlen(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hlen", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Set<String> hkeys(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hkeys(key);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hkeys", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public List<Object> hvals(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Collection<byte[]> bList = jedis.hvals(SafeEncoder.encode(key));
            List<Object> list = new ArrayList<Object>(bList.size());
            for (byte[] bts : bList) {
                list.add(deserialize(bts));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hvals", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> List<T> hvals(String key, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Collection<byte[]> bList = jedis.hvals(SafeEncoder.encode(key));
            List<T> list = new ArrayList<>(bList.size());
            for (byte[] bts : bList) {
                list.add(deserialize(bts, returnType));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis hvals", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long rpush(String key, Object... values) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        if (EmptyUtils.isEmpty(values)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bvalues = new byte[values.length][];
            for (int i = 0; i < bvalues.length; i++) {
                bvalues[i] = serialize(values[i]);
            }
            return jedis.rpush(SafeEncoder.encode(key), bvalues);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis rpush", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long lpush(String key, Object... values) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        if (EmptyUtils.isEmpty(values)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bvalues = new byte[values.length][];
            for (int i = 0; i < bvalues.length; i++) {
                bvalues[i] = serialize(values[i]);
            }
            return jedis.lpush(SafeEncoder.encode(key), bvalues);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lpush", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long llen(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.llen(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis llen", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public List<Object> lrange(String key, long start, long end) {
        if (EmptyUtils.isEmpty(key) || start < 0 || end < 0 || end < start) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();

            List<byte[]> bList = jedis.lrange(SafeEncoder.encode(key), start, end);
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<Object> list = new ArrayList<Object>(bList.size());
            for (byte[] bts : bList) {
                list.add(deserialize(bts));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lrange", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> List<T> lrange(String key, long start, long end, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key) || start < 0 || end < 0 || end < start) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            List<byte[]> bList = jedis.lrange(SafeEncoder.encode(key), start, end);
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<T> list = new ArrayList<>(bList.size());
            for (byte[] bts : bList) {
                list.add(deserialize(bts, returnType));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lrange", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public String ltrim(String key, final long start, final long end) {
        if (EmptyUtils.isEmpty(key) || start < 0 || end < 0 || end < start) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.ltrim(SafeEncoder.encode(key), start, end);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lrange", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Object lindex(String key, long index) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            return deserialize(jedis.lindex(SafeEncoder.encode(key), index));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lindex", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> T lindex(String key, long index, Class<T> returnType) {
        Object object = lindex(key, index);
        return object == null ? null : returnType.cast(object);
    }

    @Override
    public String lset(String key, long index, Object value) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.lset(SafeEncoder.encode(key), index, serialize(value));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lset", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long lrem(String key, long count, Object value) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        if (EmptyUtils.isEmpty(value)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.lrem(SafeEncoder.encode(key), count, serialize(value));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lrem", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Object lpop(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return deserialize(jedis.lpop(SafeEncoder.encode(key)));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis lpop", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> T lpop(String key, Class<T> returnType) {
        Object object = lpop(key);
        return object == null ? null : returnType.cast(object);
    }

    @Override
    public Object rpop(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return deserialize(jedis.rpop(SafeEncoder.encode(key)));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis rpop", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> T rpop(String key, Class<T> returnType) {
        Object object = rpop(key);
        return object == null ? null : returnType.cast(object);
    }

    @Override
    public Long sadd(String key, Object... members) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        if (EmptyUtils.isEmpty(members)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bmembers = new byte[members.length][];
            for (int i = 0; i < members.length; i++) {
                bmembers[i] = serialize(members[i]);
            }
            return jedis.sadd(SafeEncoder.encode(key), bmembers);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis sadd", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Set<Object> smembers(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.smembers(SafeEncoder.encode(key));
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<Object> set = new HashSet<Object>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis sadd", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> smembers(String key, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.smembers(SafeEncoder.encode(key));
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<T> set = new HashSet<>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts, returnType));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis sadd", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long srem(String key, Object... members) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(members)) {
            return 0L;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bmembers = new byte[members.length][];
            for (int i = 0; i < members.length; i++) {
                bmembers[i] = serialize(members[i]);
            }
            return jedis.srem(SafeEncoder.encode(key), bmembers);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis srem", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Object spop(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return deserialize(jedis.spop(SafeEncoder.encode(key)));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis spop", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> T spop(String key, Class<T> returnType) {
        Object result = spop(key);
        return result == null ? null : returnType.cast(result);
    }

    @Override
    public Long scard(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.scard(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis scard", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Boolean sismember(String key, String member) {
        if (EmptyUtils.isEmpty(key)) {
            return false;
        }
        if (EmptyUtils.isEmpty(member)) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.sismember(SafeEncoder.encode(key), serialize(member));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis sismember", e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    @Override
    public Object srandmember(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return deserialize(jedis.srandmember(SafeEncoder.encode(key)));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis srandmember", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> T srandmember(String key, Class<T> returnType) {
        Object re = srandmember(key);
        return re == null ? null : returnType.cast(re);
    }

    @Override
    public Long zadd(String key, double score, Object member) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(member)) {
            return 0L;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zadd(SafeEncoder.encode(key), score, serialize(member));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zadd", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long zadd(String key, Map<Double, Object> scoreMembers) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        if (EmptyUtils.isEmpty(scoreMembers)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Map<byte[], Double> bScoreMembersNew = new HashMap<byte[], Double>();
            for (Map.Entry<Double, Object> entry : scoreMembers.entrySet()) {
                bScoreMembersNew.put(serialize(entry.getValue()), entry.getKey());
            }
            return jedis.zadd(SafeEncoder.encode(key), bScoreMembersNew);

        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zadd", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long zrem(String key, Object... members) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            final byte[][] bmembers = new byte[members.length][];
            for (int i = 0; i < bmembers.length; i++) {
                bmembers[i] = serialize(members[i]);
            }
            return jedis.zrem(SafeEncoder.encode(key), bmembers);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrem", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Double zincrby(String key, double score, Object member) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(member)) {
            return 0D;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zincrby(SafeEncoder.encode(key), score, serialize(member));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrem", e);
        } finally {
            closeJedis(jedis);
        }
        return 0D;
    }

    @Override
    public Long zcard(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zcard(SafeEncoder.encode(key));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zcard", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Double zscore(String key, Object member) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zscore(SafeEncoder.encode(key), serialize(member));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zscore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Long zcount(String key, double min, double max) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zcount(SafeEncoder.encode(key), min, max);
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zcount", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long zrank(String key, Object member) {
        if (EmptyUtils.isEmpty(key)) {
            return 0L;
        }
        if (EmptyUtils.isEmpty(member)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zrank(SafeEncoder.encode(key), serialize(member));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrank", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Long zrevrank(String key, Object member) {
        if (EmptyUtils.isEmpty(key) || EmptyUtils.isEmpty(member)) {
            return 0L;
        }

        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zrevrank(SafeEncoder.encode(key), serialize(member));
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrevrank", e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    @Override
    public Set<Object> zrange(String key, long start, long end) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();

            Set<byte[]> bSet = jedis.zrange(SafeEncoder.encode(key), start, end);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<Object> set = new HashSet<Object>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrange", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> zrange(String key, long start, long end, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrange(SafeEncoder.encode(key), start, end);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<T> set = new HashSet<>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts, returnType));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrange", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Set<Object> zrevrange(String key, long start, long end) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrevrange(SafeEncoder.encode(key), start, end);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<Object> set = new HashSet<Object>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrevrange", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> zrevrange(String key, long start, long end, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrevrange(SafeEncoder.encode(key), start, end);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<T> set = new HashSet<>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts, returnType));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrevrange", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Set<Object> zrangeByScore(String key, double min, double max) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrangeByScore(SafeEncoder.encode(key), min, max);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<Object> set = new HashSet<Object>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> zrangeByScore(String key, double min, double max, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrangeByScore(SafeEncoder.encode(key), min, max);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<T> set = new HashSet<>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts, returnType));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Set<Object> zrevrangeByScore(String key, double max, double min) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<Object> set = new HashSet<Object>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> zrevrangeByScore(String key, double max, double min, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<T> set = new HashSet<>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts, returnType));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Set<Object> zrangeByScore(String key, double min, double max, int offset, int count) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrangeByScore(SafeEncoder.encode(key), min, max, offset, count);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<Object> set = new HashSet<Object>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> zrangeByScore(String key, double min, double max, int offset, int count, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrangeByScore(SafeEncoder.encode(key), min, max, offset, count);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<T> set = new HashSet<>(bSet.size());
            for (byte[] bts : bSet) {
                Object res = deserialize(bts);
                if (res != null) {
                    set.add(returnType.cast(res));
                }

            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Set<Object> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min, offset, count);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<Object> set = new HashSet<Object>(bSet.size());
            for (byte[] bts : bSet) {
                set.add(deserialize(bts));
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrevrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> zrevrangeByScore(String key, double max, double min, int offset, int count, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> bSet = jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min, offset, count);
            if (EmptyUtils.isEmpty(bSet)) {
                return null;
            }
            Set<T> set = new HashSet<>(bSet.size());
            for (byte[] bts : bSet) {
                Object res = deserialize(bts);
                if (res != null) {
                    set.add(returnType.cast(res));
                }
            }
            return set;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis zrevrangeByScore", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public List<Object> sort(String key) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            List<byte[]> bList = jedis.sort(SafeEncoder.encode(key));
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<Object> list = new ArrayList<Object>(bList.size());
            for (byte[] bts : bList) {
                list.add(deserialize(bts));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis sort", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> List<T> sort(String key, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            List<byte[]> bList = jedis.sort(SafeEncoder.encode(key));
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<T> list = new ArrayList<>(bList.size());
            for (byte[] bts : bList) {
                list.add(returnType.cast(deserialize(bts)));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis sort", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public Set<String> getKeys(String pattern) {
        if (EmptyUtils.isEmpty(pattern)) {
            return null;
        }
        Set<String> sets = new HashSet<String>();
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<String> res = jedis.keys(pattern);
            if (res != null) {
                sets.addAll(res);
            }
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            logger.error("Redis sort", e);
        } finally {
            closeJedis(jedis);
        }
        return sets;
    }

    /**
     * 发布消息
     *
     * @param channel 主题通道
     * @param message 消息
     * @return
     */
    @Override
    public Long publish(String channel, String message) {
        if (EmptyUtils.isEmpty(channel)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.publish(channel, message);
        } catch (Exception e) {
            logger.error("Redis publish ", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * 订阅消息
     *
     * @param jedisPubSub
     * @param channels    主题通道
     */
    @Override
    public void subscribe(RedisPubSub jedisPubSub, String... channels) {
        if (EmptyUtils.isEmpty(channels)) {
            logger.warn("patterns is null");
            return;
        }
        for (String channel : channels) {
            //只要包含其中一个已经订阅了，就还回
            if (RedisContext.TOPIC_SUB_MAP.containsKey(channel)) {
                return;
            }
        }
        for (String channel : channels) {
            RedisContext.TOPIC_SUB_MAP.put(channel, jedisPubSub);
        }

        try {
            Jedis jedis = getResource();
            ThreadPoolManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    jedis.subscribe(jedisPubSub, channels);
                }
            });
        } catch (Exception e) {
            logger.error("Redis subscribe ", e);
        }

    }

    /**
     * 订阅消息
     *
     * @param jedisPubSub
     * @param patterns    正则匹配 主题通道
     */
    @Override
    public void psubscribe(RedisPubSub jedisPubSub, String... patterns) {
        if (EmptyUtils.isEmpty(patterns)) {
            logger.warn("patterns is null");
            return;
        }
        for (String channel : patterns) {
            //只要包含其中一个已经订阅了，就还回
            if (RedisContext.TOPIC_PSUB_MAP.containsKey(channel)) {
                return;
            }
        }
        for (String channel : patterns) {
            RedisContext.TOPIC_PSUB_MAP.put(channel, jedisPubSub);
        }

        try {
            Jedis jedis = getResource();
            ThreadPoolManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    jedis.psubscribe(jedisPubSub, patterns);
                }
            });
        } catch (Exception e) {
            logger.error("Redis psubscribe ", e);
        }

    }


    @Override
    public List<Object> sort(String key, SortingParams sortingParameters) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = getResource();
        try {
            List<byte[]> bList = jedis.sort(SafeEncoder.encode(key), sortingParameters);
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<Object> list = new ArrayList<Object>(bList.size());
            for (byte[] bts : bList) {
                list.add(deserialize(bts));
            }
            return list;
        } catch (Exception je) {
            returnBrokenResource(jedis, je);

        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <T> List<T> sort(String key, SortingParams sortingParameters, Class<T> returnType) {
        if (EmptyUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = getResource();
        try {
            List<byte[]> bList = jedis.sort(SafeEncoder.encode(key), sortingParameters);
            if (EmptyUtils.isEmpty(bList)) {
                return null;
            }
            List<T> list = new ArrayList<>(bList.size());
            for (byte[] bts : bList) {
                list.add(returnType.cast(deserialize(bts)));
            }
            return list;
        } catch (JedisException je) {
            returnBrokenResource(jedis, je);
        } catch (Exception e) {
            returnBrokenResource(jedis, e);
            logger.error("Redis sort", e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Jedis getResource() {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            logger.error("getResource", e);
            throw new JedisConnectionException(e);
        }
    }

    public void closeJedis(Jedis jedis) {
        if (null != jedis) {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.error("closeJedis", e);
            }
        }
    }


    private void returnBrokenResource(Jedis jedis, Exception je) {
        if (null != jedis) {
            try {
                logger.error(je.getMessage(), je);
                jedis.close();
            } catch (Exception e) {
                logger.error("returnBrokenResource", e);
            } finally {
                jedis = null;
            }
        }
    }


    public Jedis getJedis() {
        return getResource();
    }

    private byte[] serialize(Object obj) {
        return SerializerFactory.serialize(obj);
    }

    private Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return SerializerFactory.deserialize(bytes);
    }

    private <T> T deserialize(byte[] bytes, Class<T> returnType) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return SerializerFactory.deserialize(bytes, returnType);
    }
}
