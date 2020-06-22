package com.zq.thread.group;

/**
 * @author zhangqing
 * @Package com.zq.thread.group
 * @date 2020/6/18 17:39
 */
public class ThreadGroupDemo {
    public static void main(String[] args) {
        new ThreadGroupDemo().test03();
    }

    private void test01(){
        Thread testThread = new Thread(() -> {
            System.out.println("testThread当前线程组名字：" + Thread.currentThread().getThreadGroup().getName());
            System.out.println("testThread线程名字：" + Thread.currentThread().getName());
        });

        testThread.start();
        System.out.println("执行main方法线程名字：" + Thread.currentThread().getName());
    }

    /**
     * 复制线程组
     */
    private void test02(){
        //复制一个线程组到一个线程组
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
    }

    /**
     * 线程组统一异常处理
     */
    private void test03(){
        ThreadGroup threadGroup1 = new ThreadGroup("group1"){
            //继承ThreadGroup并重新定义以下方法
            //在线程成员抛出unchecked exception
            //会执行方法
            @Override
            public void uncaughtException(Thread t, Throwable e){
                System.out.println(t.getName() + ": " + e.getMessage());
            }
        };

        //这个线程是threadGroup1的一员
        Thread thread = new Thread(threadGroup1, new Runnable() {
            @Override
            public void run() {
                //抛出unchecked异常
                throw new RuntimeException("测试异常");
            }
        });
        thread.start();
    }
}
