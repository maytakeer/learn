package com.zq.thread.locks;

import java.util.concurrent.locks.StampedLock;

/** StampedLock使用 java8发布的，适用于在读线程非常多而写线程非常少。性能比ReentrantReadWriteLock更好
 * @author zhangqing
 * @Package com.zq.thread.locks
 * @date 2020/6/22 17:40
 */
public class StampedLockDemo {

    private double x,y;

    private final StampedLock s1 = new StampedLock();

    /**
     * 写锁的使用
     */
    void move(double deltaX, double deltaY){
        long stamp = s1.writeLock();
        try{
            x += deltaX;
            y += deltaY;
        }finally {
            //释放写锁
            s1.unlockWrite(stamp);
        }
    }

    /**
     * 乐观读锁的使用
     */
    double distanceFromOrigin(){
        long stamp = s1.tryOptimisticRead();
        double currentX = x, currentY = y;
        //检查乐观读锁后是否有其他写锁发送，有则返回false
        if (!s1.validate(stamp)){
            //获取一个悲观读锁
            stamp = s1.readLock();
            try{
                currentX = x;
                currentY = y;
            }finally {
                //释放悲观读锁
                s1.unlockWrite(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    /**
     *  悲观读锁以及读锁升级写锁的使用
     */
    void moveIfAtOrigin(double newX, double newY){
        //悲观读锁
        long stamp = s1.readLock();
        try{
            while (x == 0.0 && y == 0.0){
                //读锁尝试转换为写锁：转换成功后相当于获取了写锁，转换失败相当于有写锁被占用
                long ws = s1.tryConvertToWriteLock(stamp);

                //如果转化成功
                if (ws != 0L){
                    //读锁的票据更新为写锁的
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                }else {
                    //释放读锁
                    s1.unlockRead(stamp);
                    //强制获取写锁
                    stamp = s1.writeLock();
                }
            }
        }finally {
            s1.unlock(stamp);
        }
    }


}
