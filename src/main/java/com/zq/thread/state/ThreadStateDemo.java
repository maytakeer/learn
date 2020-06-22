package com.zq.thread.state;

/**
 * java线程6个状态
 *   NEW,
 *   RUNNABLE,
 *   BLOCKED,
 *   WAITING,
 *   TIMED_WAITING,
 *   TERMINATED;
 * @author zhangqing
 * @Package com.zq.thread.state
 * @date 2020/6/19 10:38
 */
public class ThreadStateDemo {
    public static void main(String[] args) {
//        new ThreadStateDemo().testStateNew();
        new ThreadStateDemo().blockedTest();
    }

    /**
     * NEW状态：线程尚未启动，没有调用start()方法
     */
    private void testStateNew(){
        Thread thread = new Thread(() -> {});
        thread.start();
        thread.start();
        //输出NEW
        System.out.println(thread.getState());
    }

    private void blockedTest(){
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        },"a");

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "b");

        a.start();
        try{
//            Thread.sleep(1000);
//            a.join();
            a.join(1000);
        }catch (Exception e){

        }
        b.start();
        System.out.println(a.getName() + ":" + a.getState());
        System.out.println(b.getName() + ":" + b.getState());
    }

    /**
     * 同步方法争夺锁
     */
    private synchronized void testMethod(){
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
