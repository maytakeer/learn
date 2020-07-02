package com.zq.thread.container;

import java.util.Vector;

/**
 * 如果方法一和方法二为一个组合的话。那么当方法一获取到了vector的size之后，方法二已经执行完毕，这样就导致程序的错误。
 * 如果方法三与方法四组合的话。通过锁机制保证了在vector上的操作的原子性。
 * @author zhangqing
 * @Package com.zq.thread.container
 * @date 2020/6/28 15:24
 */
public class TestVector {

    private Vector<String> vector;

    /**
     * 方法一
     * @param vector
     * @return
     */
    public Object getLast(Vector vector){
        int lastIndex = vector.size() - 1;
        return vector.get(lastIndex);
    }

    /**
     * 方法二
     * @param vector
     */
    public void deleteLast(Vector vector){
        int lastIndex = vector.size() - 1;
        vector.remove(lastIndex);
    }

    /**
     * 方法三
     * @param vector
     * @return
     */
    public Object getLastSysnchronized(Vector vector){
        synchronized (vector){
            int lastIndex = vector.size() - 1;
            return vector.get(lastIndex);
        }
    }

    /**
     * 方法四
     * @param vector
     */
    public void deleteLastSysnchronized(Vector vector){
        synchronized (vector){
            int lastIndex = vector.size() - 1;
            vector.remove(lastIndex);
        }
    }

}
