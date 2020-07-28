package com.zq.redis.lock.locker;

/**
 * @author zhangqing
 * @Package com.zq.redis.lock.locker
 * @date 2020/7/25 15:05
 */
public interface RollBack {
    void execute();

    void execute(Exception ex);
}
