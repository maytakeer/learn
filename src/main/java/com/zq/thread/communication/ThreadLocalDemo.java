package com.zq.thread.communication;

/** ThreadLocal为每个线程都创建一个副本，每个线程可以访问自己内部的副本变量。
 * @author zhangqing
 * @Package com.zq.thread.communication
 * @date 2020/6/19 16:52
 */
public class ThreadLocalDemo {
    static class ThreadA implements Runnable{

        private ThreadLocal<String> threadLocal;

        public ThreadA(ThreadLocal<String> threadLocal){
            this.threadLocal = threadLocal;
        }

        @Override
        public void run() {
            threadLocal.set("A");
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("ThreadA输出:" + threadLocal.get());
        }

        static class ThreadB implements Runnable {
            private ThreadLocal<String> threadLocal;

            public ThreadB(ThreadLocal<String> threadLocal) {
                this.threadLocal = threadLocal;
            }

            @Override
            public void run() {
                threadLocal.set("B");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadB输出：" + threadLocal.get());
            }
        }

        public static void main(String[] args) {
            ThreadLocal<String> threadLocal = new ThreadLocal<>();
            new Thread(new ThreadA(threadLocal)).start();
            new Thread(new ThreadB(threadLocal)).start();
        }
    }

}
