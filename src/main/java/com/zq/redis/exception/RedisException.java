package com.zq.redis.exception;

/**
 * @author zhangqing
 * @Package com.zq.redis.exception
 * @date 2020/7/28 16:41
 */
public class RedisException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RedisException() {
        super();
    }

    public RedisException(String message) {
        super(message);
    }

    public RedisException(Throwable cause) {
        super(cause);
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }
}
