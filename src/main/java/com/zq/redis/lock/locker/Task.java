package com.zq.redis.lock.locker;

/**
 * @author zhangqing
 * @Package com.zq.redis.lock.locker
 * @date 2020/7/25 14:21
 */
public interface Task<E> {
    E run();
}
