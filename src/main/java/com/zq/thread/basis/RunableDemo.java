package com.zq.thread.basis;

/**
 * @author zhangqing
 * @Package com.zq.thread.basis
 * @date 2020/6/18 16:57
 */
public class RunableDemo {

    public static class MyThread implements Runnable{
        @Override
        public void run() {
            System.out.println("MyThread");
        }
    }

    public static void main(String[] args) {
        new Thread(new MyThread()).start();

        //Java 8 函数式编程，可以省略MyThread类
        new Thread(() -> {
            System.out.println("Java 8 匿名内部类");
        }).start();
    }
}
