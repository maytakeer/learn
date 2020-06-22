package com.zq.thread.basis;


/**
 * @author zhangqing
 * @Package com.zq.thread.basis
 * @date 2020/6/18 16:52
 */
public class ThreadDemo {

    public static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("MyThread");
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        // 静态方法，返回对当前正在执行的线程对象的引用
        Thread thread = MyThread.currentThread();

    }
}
