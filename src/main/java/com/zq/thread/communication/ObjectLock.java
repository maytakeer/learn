package com.zq.thread.communication;

/** 线程同步先打印A线程，在打印B线程测试
 * @author zhangqing
 * @Package com.zq.thread.communication
 * @date 2020/6/19 15:49
 */
public class ObjectLock {

    private static Object lock = new Object();

    static class ThreadA implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                for (int i = 0; i < 100; i++){
                    System.out.println("Thread A" + i);
                }
            }
        }
    }

    static class ThreadB implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                for (int i = 0; i < 100; i++){
                    System.out.println("Thread B" + i);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new NoneLock.ThreadA()).start();
//        try{
//            Thread.sleep(10);
//        }catch (Exception e){
//
//        }
        new Thread(new NoneLock.ThreadB()).start();
    }
}
