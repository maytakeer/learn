package com.zq.redis;

import com.zq.redis.callback.RedisPubSub;
import redis.clients.jedis.SortingParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangqing
 * @Package com.zq.redis
 * @date 2020/7/25 15:11
 */
public interface Redis {

    /**
     * 确认key是否存在.
     * <p>
     * key存在,返回true;否则,返回false.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Boolean exists(String key);

    /**
     * 撤销即将过期的key,使其能继续使用.
     * <p>
     * key可以继续使用,返回1L;否则,返回0L.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long persist(String key);

    /**
     * 返回值的类型.
     * <p>
     * Redis中的数据类型,如:String,Set等.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    String type(String key);

    /**
     * 设置key的有效时间.
     * <p>
     * 设置成功,返回1L;设置失败,返回0L.
     *
     * @param key
     * @param seconds 过期时间,单位为秒
     * @return
     * @author liudejian 2013-11-29
     */
    Long expire(String key, int seconds);

    /**
     * 设置一组key的有效时间.
     * <p>
     * 设置成功,返回>=1L;设置失败,返回0L.
     *
     * @param map key=>key, value=>过期时间
     * @return
     */
    Long expire(Map<String, Integer> map);

    /**
     * 设置一个key的到期时间.
     * <p>
     * 设置成功,返回1L;设置失败,返回0L.
     *
     * @param key
     * @param unixTime 相对1970后经过的秒数
     * @return
     * @author liudejian 2013-11-29
     */
    Long expireAt(String key, Long unixTime);

    /**
     * 设置一组key的到期时间.
     * <p>
     * 设置成功,返回>=1L;设置失败,返回0L.
     *
     * @param map key=>key, value=>到期时间,相对1970后经过的秒数
     * @return
     */
    Long expireAt(Map<String, Long> map);

    /**
     * 获得一个key的有效时间.
     * <p>
     * 返回Key的有效时间,单位为秒.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long ttl(String key);

    /**
     * 删除指定的key.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long del(String key);

    /**
     * 删除指定的一组key.
     *
     * @param keys
     * @return
     * @author liudejian 2013-11-29
     */
    Long del(String... keys);

    /**
     * 缓存一个字符串.
     * <p>
     * 只能与getString(key)配合使用.<br>
     * 缓存成功后无法通过getObject(key)接口取出正确数据.<br>
     * 若后续需要对此String进行incr/decr/incrBy/decrBy操作,则不能用setObject替换.
     * <p>
     * getString(String)
     * setString(String, int, String)
     *
     * @param key
     * @param value
     * @return "OK" or "failed"
     * @author liudejian 2013-11-29
     */
    String setString(String key, String value);

    /**
     * 缓存一个字符串.
     * <p>
     * 只能与getString(key)配合使用.<br>
     * 缓存成功后无法通过getObject(key)接口取出正确数据.<br>
     * 若后续需要对此String进行incr/decr/incrBy/decrBy操作,则不能用setObject替换.
     * <p>
     * getString(String)
     * setString(String, String)
     *
     * @param key
     * @param seconds
     * @param value
     * @return "OK" or "failed"
     * @author liudejian 2013-11-29
     */
    String setString(String key, int seconds, String value);

    /**
     * 缓存一个字符串.
     * <p>
     * 只能与setString()配合使用.<br>
     * 通过setObject(key,value)之类的接口缓存的对象,无法通过此接口取出正确数据.
     * <p>
     * setString(String, String)
     * setString(String, int, String)
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    String getString(String key);

    /**
     * 缓存新的字符串,并返回旧的字符串.
     * <p>
     * 字符串不能大于1GB.
     *
     * @param key
     * @param value
     * @return
     * @author liudejian 2013-11-29
     */
    String getSetString(String key, String value);

    /**
     * 缓存一个字符串.
     * <p>
     * 只能与getObject()配合使用.<br>
     * 缓存成功后无法通过getString(key)接口取出正确数据.<br>
     * 若后续需要对此String进行incr/decr/incrBy/decrBy操作,则不能用此方法.
     * <p>
     * getObject(String)
     * setObject(String, int, Object)
     *
     * @param key
     * @param value
     * @return "OK" or "failed"
     * @author liudejian 2013-11-29
     */
    String setObject(String key, Object value);

    /**
     * 缓存一个字符串.
     * <p>
     * 只能与getObject()配合使用.<br>
     * 缓存成功后无法通过getString(key)接口取出正确数据.<br>
     * 若后续需要对此String进行incr/decr/incrBy/decrBy操作,则不能用此方法.
     * <p>
     * getObject(String)
     * setObject(String, Object)
     *
     * @param key
     * @param seconds
     * @param value
     * @return "OK" or "failed"
     * @author liudejian 2013-11-29
     */
    String setObject(String key, int seconds, Object value);

    /**
     * 缓存一个字符串.
     * <p>
     * 只能与setObject()配合使用.<br>
     * 通过setString(key,value)之类的接口缓存的对象,无法通过此接口取出正确数据.
     * <p>
     * setObject(String, Object)
     * setObject(String, int, Object)
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Object getObject(String key);

    <T> T getObject(String key, Class<T> returnType);

    /**
     * 缓存新的对象,并返回旧的对象.
     * <p>
     * 对象不能大于1GB.
     *
     * @param key
     * @param value
     * @return
     * @author liudejian 2013-11-29
     */
    Object getSetObject(String key, Object value);

    /**
     * 缓存新的对象,并返回旧的对象.
     *
     * @param key
     * @param value
     * @param returnType
     * @param <T>
     * @return
     */
    <T> T getSetObject(String key, Object value, Class<T> returnType);

    /**
     * 对key做自增1.
     * <p>
     * 如果key不存在,则在执行此操作前设置默认值为0.<br>
     * 如果key存在,且key是十进制数值的字符串表示(e.g: "-123L"),则在此数据基础上自增(e.g:return -122L).<br>
     * 如果key存在,且key不是十进制数值的字符串表示,则返回null.
     * <p>
     * decr(String)
     * incrBy(String, long)
     * decrBy(String, long)
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long incr(String key);

    /**
     * 对key做自增N.
     * <p>
     * decrBy(String, long)
     * decr(String)
     * incr(String)
     *
     * @param key
     * @param integer
     * @return
     * @author liudejian 2013-11-29
     */
    Long incrBy(String key, long integer);

    /**
     * 对key做自减1.
     * <p>
     * 如果key不存在,则在执行此操作前设置默认值为0.<br>
     * 如果key存在,且key是十进制数值的字符串表示(e.g: "-123L"),则在此数据基础上自增(e.g:return -124L).<br>
     * 如果key存在,且key不是十进制数值的字符串表示,则返回null.
     * <p>
     * incr(String)
     * incrBy(String, long)
     * decrBy(String, long)
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long decr(String key);

    /**
     * 对key做自减N.
     * <p>
     * incrBy(String, long)
     * decr(String)
     * incr(String)
     *
     * @param key
     * @param integer
     * @return
     * @author liudejian 2013-11-29
     */
    Long decrBy(String key, long integer);

    /**
     * 向名称为key的hash中添加元素field<—>value.
     * <p>
     * 如果字段已经存在,则更新field值,返回0L;<br>
     * 否则创建一新的field,返回1L.
     *
     * @param key
     * @param field
     * @param value
     * @return
     * @author liudejian 2013-11-29
     */
    Long hset(String key, String field, Object value);

    /**
     * 向名称为key的hash中添加元素field i<—>value i.
     * <p>
     * 操作成功,返回true;<br>
     * 否则,返回false.
     *
     * @param key
     * @param hash
     * @return
     * @author liudejian 2013-11-29
     */
    Boolean hmset(String key, Map<String, Object> hash);

    /**
     * 返回名称为key的hash中field对应的value.
     *
     * @param key
     * @param field
     * @return
     * @author liudejian 2013-11-29
     */
    Object hget(String key, String field);

    /**
     * 返回名称为key的hash中field对应的value.
     *
     * @param key
     * @param field
     * @param returnType
     * @param <T>
     * @return
     */
    <T> T hget(String key, String field, Class<T> returnType);

    /**
     * 返回名称为key的hash中所有的键(field)及其对应的value.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Map<String, Object> hgetAll(String key);

    <T> Map<String, T> hgetAll(String key, Class<T> returnType);

    /**
     * 返回名称为key的hash中field i对应的value.
     *
     * @param key
     * @param fields
     * @return
     * @author liudejian 2013-11-29
     */
    List<Object> hmget(String key, String... fields);

    <T> List<T> hmget(String key, Class<T> returnType, String... fields);

    /**
     * 名称为key的hash中是否存在键为field的域.
     * <p>
     * 存在,返回true;<br>
     * 否则,返回false.
     *
     * @param key
     * @param field
     * @return
     * @author liudejian 2013-11-29
     */
    Boolean hexists(String key, String field);

    /**
     * 删除名称为key的hash中键为field i的域.
     * <p>
     * 如果存在,删除,返回1L;<br>
     * 否则,返回0L,不执行操作.
     *
     * @param key
     * @param fields
     * @return
     * @author liudejian 2013-11-29
     */
    Long hdel(final String key, final String... fields);

    /**
     * 返回名称为key的hash中元素个数.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long hlen(String key);

    /**
     * 返回名称为key的hash中所有键.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Set<String> hkeys(String key);

    /**
     * 返回名称为key的hash中所有键对应的value.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    List<Object> hvals(String key);

    <T> List<T> hvals(String key, Class<T> returnType);

    /**
     * 在名称为key的list尾添加一个值为value的元素.
     * <p>
     * 操作完成后的list长度.
     *
     * @param key
     * @param values
     * @return
     * @author liudejian 2013-11-29
     */
    Long rpush(String key, Object... values);

    /**
     * 在名称为key的list头添加一个值为value的 元素.
     *
     * @param key
     * @param values
     * @return
     * @author liudejian 2013-11-29
     */
    Long lpush(String key, Object... values);

    /**
     * 返回名称为key的list的长度.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long llen(String key);

    /**
     * 返回名称为key的list中start至end之间的元素(下标从0开始).
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author liudejian 2013-11-29
     */
    List<Object> lrange(String key, long start, long end);

    <T> List<T> lrange(String key, long start, long end, Class<T> returnType);

    /**
     * 截取名称为key的list,保留start至end之间的元素.
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author liudejian 2013-11-29
     */
    String ltrim(String key, long start, long end);

    /**
     * 返回名称为key的list中index位置的元素.
     *
     * @param key
     * @param index
     * @return
     * @author liudejian 2013-11-29
     */
    Object lindex(String key, long index);

    <T> T lindex(String key, long index, Class<T> returnType);

    /**
     * 给名称为key的list中index位置的元素赋值为value.
     *
     * @param key
     * @param index
     * @param value
     * @return
     * @author liudejian 2013-11-29
     */
    String lset(String key, long index, Object value);

    /**
     * 删除count个名称为key的list中值为value的元素.
     * <p>
     * count=0,删除所有值为value的元素;<br>
     * count>0,从头至尾删除count个值为value的元素;<br>
     * count<0,从尾到头删除count个值为value的元素.
     *
     * @param key
     * @param count
     * @param value
     * @return
     * @author liudejian 2013-11-29
     */
    Long lrem(String key, long count, Object value);

    /**
     * 返回并删除名称为key的list中的首元素.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Object lpop(String key);

    <T> T lpop(String key, Class<T> returnType);

    /**
     * 返回并删除名称为key的list中的尾元素.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Object rpop(String key);

    <T> T rpop(String key, Class<T> returnType);

    /**
     * 向名称为key的set中添加元素member i.
     *
     * @param key
     * @param members
     * @return
     * @author liudejian 2013-11-29
     */
    Long sadd(String key, Object... members);

    /**
     * 返回名称为key的set的所有元素.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Set<Object> smembers(String key);

    <T> Set<T> smembers(String key, Class<T> returnType);

    /**
     * 删除名称为key的set中的元素member i.
     *
     * @param key
     * @param members
     * @return
     * @author liudejian 2013-11-29
     */
    Long srem(String key, Object... members);

    /**
     * 随机返回并删除名称为key的set中一个元素.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Object spop(String key);

    <T> T spop(String key, Class<T> returnType);

    /**
     * 返回名称为key的set的基数.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long scard(String key);

    /**
     * 测试member是否是名称为key的set的元素.
     *
     * @param key
     * @param member
     * @return
     * @author liudejian 2013-11-29
     */
    Boolean sismember(String key, String member);

    /**
     * 随机返回名称为key的set的一个元素.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Object srandmember(String key);

    <T> T srandmember(String key, Class<T> returnType);

    /**
     * 向名称为key的zset中添加元素member,score用于排序.
     * <p>
     * 如果该元素已经存在,则根据score更新该元素的顺序.
     *
     * @param key
     * @param score
     * @param member
     * @return
     * @author liudejian 2013-11-29
     */
    Long zadd(String key, double score, Object member);

    /**
     * 向名称为key的zset中添加元素member i,score i用于排序.
     * <p>
     * 如果该元素已经存在,则根据score更新该元素的顺序.
     *
     * @param key
     * @param scoreMembers
     * @return
     * @author liudejian 2013-11-29
     */
    Long zadd(String key, Map<Double, Object> scoreMembers);

    /**
     * 删除名称为key的zset中的元素member i.
     *
     * @param key
     * @param members
     * @return
     * @author liudejian 2013-11-29
     */
    Long zrem(String key, Object... members);

    /**
     * 如果在名称为key的zset中已经存在元素member,则该元素的score增加increment;<br>
     * 否则向集合中添加该元素,其score的值为increment.
     *
     * @param key
     * @param score
     * @param member
     * @return
     * @author liudejian 2013-11-29
     */
    Double zincrby(String key, double score, Object member);

    /**
     * 返回名称为key的zset的基数.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    Long zcard(String key);

    /**
     * 返回名称为key的zset中元素element的score.
     *
     * @param key
     * @param member
     * @return
     * @author liudejian 2013-11-29
     */
    Double zscore(String key, Object member);

    /**
     * 返回名称为key的zset中score >= min且score <= max的元素个数.
     *
     * @param key
     * @param min
     * @param max
     * @return
     * @author liudejian 2013-11-29
     */

    Long zcount(String key, double min, double max);

    /**
     * 返回名称为key的zset(元素已按score从小到大排序)中member元素的rank(即index,从0开始),<br>
     * 若没有member元素,返回 "nil".
     *
     * @param key
     * @param member
     * @return
     * @author liudejian 2013-11-29
     */
    Long zrank(String key, Object member);

    /**
     * 返回名称为key的zset(元素已按score从大到小排序)中member元素的rank(即index,从0开始),<br>
     * 若没有member元素,返回 "nil".
     *
     * @param key
     * @param member
     * @return
     * @author liudejian 2013-11-29
     */
    Long zrevrank(String key, Object member);

    /**
     * 返回名称为key的zset(元素已按score从小到大排序)中的index从start到end的所有元素.
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author liudejian 2013-11-29
     */
    Set<Object> zrange(String key, long start, long end);

    <T> Set<T> zrange(String key, long start, long end, Class<T> returnType);

    /**
     * 返回名称为key的zset(元素已按score从大到小排序)中的index从start到end的所有元素.
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author liudejian 2013-11-29
     */
    Set<Object> zrevrange(String key, long start, long end);

    <T> Set<T> zrevrange(String key, long start, long end, Class<T> returnType);

    /**
     * 返回名称为key的zset中score >= min且score <= max的所有元素(元素已按score从小到大排序).
     *
     * @param key
     * @param min
     * @param max
     * @return
     * @author liudejian 2013-11-29
     */
    Set<Object> zrangeByScore(String key, double min, double max);

    <T> Set<T> zrangeByScore(String key, double min, double max, Class<T> returnType);

    /**
     * 返回名称为key的zset中score >= min且score <= max的所有元素(元素已按score从大到小排序).
     *
     * @param key
     * @param max
     * @param min
     * @return
     * @author liudejian 2013-11-29
     */
    Set<Object> zrevrangeByScore(String key, double max, double min);

    <T> Set<T> zrevrangeByScore(String key, double max, double min, Class<T> returnType);

    /**
     * 返回名称为key的zset中score >= min且score <= max的count个元素(元素已按score从小到大排序).
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     * @author liudejian 2013-11-29
     */
    Set<Object> zrangeByScore(String key, double min, double max, int offset, int count);

    <T> Set<T> zrangeByScore(String key, double min, double max, int offset, int count, Class<T> returnType);

    /**
     * 返回名称为key的zset中score >= min且score <= max的count个元素(元素已按score从大到小排序).
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     * @author liudejian 2013-11-29
     */
    Set<Object> zrevrangeByScore(String key, double max, double min, int offset, int count);

    <T> Set<T> zrevrangeByScore(String key, double max, double min, int offset, int count, Class<T> returnType);

    /**
     * 排序.
     *
     * @param key
     * @return
     * @author liudejian 2013-11-29
     */
    List<Object> sort(String key);

    <T> List<T> sort(String key, Class<T> returnType);

    /**
     * 排序.
     *
     * @param key
     * @param sortingParameters
     * @return
     * @author liudejian 2013-11-29
     */
    List<Object> sort(String key, SortingParams sortingParameters);

    <T> List<T> sort(String key, SortingParams sortingParameters, Class<T> returnType);

    /**
     * 根据正则或去对应的key
     *
     * @param parttern
     * @return
     */
    Set<String> getKeys(String parttern);

    /**
     * 发布消息
     *
     * @param channel 主题通道
     * @param message 消息
     * @return
     */
    Long publish(String channel, String message);

    /**
     * 订阅消息
     *
     * @param channels    主题通道
     * @param jedisPubSub
     */
    void subscribe(RedisPubSub jedisPubSub, String... channels);


    /**
     * 订阅消息
     *
     * @param patterns    正则匹配 主题通道
     * @param jedisPubSub
     */
    void psubscribe(RedisPubSub jedisPubSub, String... patterns);
}
