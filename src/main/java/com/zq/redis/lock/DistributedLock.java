package com.zq.redis.lock;

import com.zq.redis.lock.locker.Locker;
import com.zq.redis.lock.locker.RollBack;
import com.zq.redis.lock.locker.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangqing
 * @Package com.zq.redis.lock
 * @date 2020/7/28 16:37
 */
public class DistributedLock {

    /**
     * times 重试获取锁次数
     */
    private static final int RETRY_COUNT = 1;
    /**
     * millisecond  所获取过期时间
     */
    private static final long EXPIRE_TIME = 1000;
    /**
     * millisecond : 未获取锁时，间隔等待所时间
     */
    private static final long DELAY_TIME = 100;
    private static Logger log = LoggerFactory.getLogger(DistributedLock.class);
    public Locker locker;
    public DistributedLock(Locker locker) {
        this.locker = locker;
    }

    public DistributedLock setLocker(Locker locker) {
        this.locker = locker;
        return this;
    }

    /**
     * @param task 待执行任务
     * @param id   资源KEY
     * @param <E>
     * @return
     * @throws InterruptedException
     * @throws NotFetchLockException
     */
    public <E> E execute(Task<E> task, String id) throws InterruptedException, NotFetchLockException {
        return execute(task, id, RETRY_COUNT, DELAY_TIME, EXPIRE_TIME);
    }

    /**
     * @param task     待执行任务
     * @param id       资源KEY
     * @param runnable 异常回调(异步)
     * @param <E>
     * @return
     * @throws InterruptedException
     * @throws NotFetchLockException
     */
    public <E> E execute(Task<E> task, String id, Runnable runnable) throws InterruptedException, NotFetchLockException {
        return execute(task, id, RETRY_COUNT, DELAY_TIME, EXPIRE_TIME, runnable);
    }

    /**
     * @param task     待执行任务
     * @param id       资源KEY
     * @param rollBack 异常回调(同步)
     * @param <E>
     * @return
     * @throws InterruptedException
     * @throws NotFetchLockException
     */
    public <E> E execute(Task<E> task, String id, RollBack rollBack) throws InterruptedException, NotFetchLockException {
        return execute(task, id, RETRY_COUNT, DELAY_TIME, EXPIRE_TIME, rollBack);
    }

    public <E> E execute(Task<E> task, String id, int retryCount) throws InterruptedException, NotFetchLockException {
        return execute(task, id, retryCount, DELAY_TIME, EXPIRE_TIME);
    }

    public <E> E execute(Task<E> task, String id, int retryCount, Runnable runnable) throws InterruptedException, NotFetchLockException {
        return execute(task, id, retryCount, DELAY_TIME, EXPIRE_TIME, runnable);
    }

    public <E> E execute(Task<E> task, String id, int retryCount, RollBack rollBack) throws InterruptedException, NotFetchLockException {
        return execute(task, id, retryCount, DELAY_TIME, EXPIRE_TIME, rollBack);
    }

    public <E> E execute(Task<E> task, String id, int retryCount, long expiredTime) throws InterruptedException, NotFetchLockException {
        return execute(task, id, retryCount, DELAY_TIME, expiredTime);
    }

    public <E> E execute(Task<E> task, String id, int retryCount, long expiredTime, Runnable runnable) throws InterruptedException, NotFetchLockException {
        return execute(task, id, retryCount, DELAY_TIME, expiredTime, runnable);
    }

    public <E> E execute(Task<E> task, String id, int retryCount, long expiredTime, RollBack rollBack) throws InterruptedException, NotFetchLockException {
        return execute(task, id, retryCount, DELAY_TIME, expiredTime, rollBack);
    }

    /**
     * @param task        待执行任务
     * @param id          资源KEY
     * @param retryCount  重试次数
     * @param delayTime   延迟时间
     * @param expiredTime 锁超时时间
     * @param <E>
     * @return
     * @throws InterruptedException
     * @throws NotFetchLockException
     */
    public <E> E execute(Task<E> task, String id, final int retryCount, long delayTime, long expiredTime) throws InterruptedException, NotFetchLockException {
        final String key = "lock:" + id;
        long getLock = 0;
        int cnt = retryCount;
        while (getLock == 0 && (retryCount == -1 || cnt-- > 0)){
            getLock = locker.tryLock(key, expiredTime);
            if (getLock > 0){
                try {
                    return task.run();
                }finally {
                    locker.unLock(key, getLock);
                }
            }
            Thread.sleep(delayTime);
        }

        log.warn("not fetch lock for id={} retryCount={} delayTime(ms)={}", id, retryCount, delayTime);
        return null;
    }

    /**
     * @param task        待执行任务
     * @param id          资源KEY
     * @param retryCount  重试次数
     * @param delayTime   延迟时间
     * @param expiredTime 锁超时时间
     * @param runnable    异常异步回调
     * @param <E>
     * @return
     * @throws InterruptedException
     * @throws NotFetchLockException
     */
    public <E> E execute(Task<E> task, String id, final int retryCount, long delayTime, long expiredTime, Runnable runnable) throws InterruptedException, NotFetchLockException {
        final String key = "lock:" + id;
        long getLock = 0;
        int cnt = retryCount;
        while (getLock == 0 && (retryCount == -1 || cnt-- > 0)) {
            getLock = locker.tryLock(key, expiredTime);
            if (getLock > 0) {
                try {
                    return task.run();
                } catch (Exception ex) {
                    if (runnable != null) {
                        runnable.run();
                    }
                } finally {
                    locker.unLock(key, getLock);
                }
            }
            Thread.sleep(delayTime);
        }

        log.warn("not fetch lock for id={} retryCount={} delayTime(ms)={}", id, retryCount, delayTime);
        return null;
    }

    /**
     * @param task        待执行任务
     * @param id          资源KEY
     * @param retryCount  重试次数
     * @param delayTime   延迟时间
     * @param expiredTime 锁超时时间
     * @param rollBack    异常同步回调
     * @param <E>
     * @return
     * @throws InterruptedException
     * @throws NotFetchLockException
     */
    public <E> E execute(Task<E> task, String id, final int retryCount, long delayTime, long expiredTime, RollBack rollBack) throws InterruptedException, NotFetchLockException {
        try {
            final String key = "lock:" + id;

            long getLock = 0;

            int cnt = retryCount;

            while (getLock == 0 && (retryCount == -1 || cnt-- > 0)) {
                getLock = locker.tryLock(key, expiredTime);
                if (getLock > 0) {
                    try {
                        return task.run();
                    } catch (Exception ex) {
                        if (rollBack != null) {
                            rollBack.execute(ex);
                        }
                    } finally {
                        locker.unLock(key, getLock);
                    }
                }
                Thread.sleep(delayTime);
            }

            log.warn("not fetch lock for id={} retryCount={} delayTime(ms)={}", id, retryCount, delayTime);
        } catch (Exception ex) {
            if (rollBack != null) {
                rollBack.execute(ex);
            }
        } finally {
            log.warn("not fetch lock for id={} retryCount={} delayTime(ms)={}", id, retryCount, delayTime);
        }
        return null;
    }

    public long getLock(int expiredTime, final String key) {
        try {
            return locker.tryLock(key, expiredTime);
        } catch (Exception e) {
            log.info("try to get Lock Exception:" + e.getMessage());
        }
        return 0;
    }

    class NotFetchLockException extends RuntimeException {
        private static final long serialVersionUID = -6490056205468921965L;

        public NotFetchLockException(String message) {
            super(message);
        }
    }
}
