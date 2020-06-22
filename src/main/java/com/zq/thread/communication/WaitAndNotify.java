package com.zq.thread.communication;

/** 两个线程交替打印1234
 * @author zhangqing
 * @Package com.zq.thread.communication
 * @date 2020/6/19 15:58
 */
public class WaitAndNotify {
    private static Object lock = new Object();

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock){
                for (int i = 0; i < 5; i++){
                    try{
                        System.out.println("ThreadA：" + i);
                        lock.notify();
                        lock.wait();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("ThreadB: " + i);
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
//        Thread.sleep(1000);
        new Thread(new ThreadB()).start();
    }
}
