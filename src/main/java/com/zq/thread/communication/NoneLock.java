package com.zq.thread.communication;

/** 无锁测试
 * @author zhangqing
 * @Package com.zq.thread.communication
 * @date 2020/6/19 15:42
 */
public class NoneLock {

    static class ThreadA implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++){
                System.out.println("Thread A" + i);
            }
        }
    }

    static class ThreadB implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++){
                System.out.println("Thread B" + i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
    }
}
