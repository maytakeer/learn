package com.zq.thread.communication;

/** volatile实现 线程A输出0，然后线程B输出1，再然后线程A输出2
 * @author zhangqing
 * @Package com.zq.thread.communication
 * @date 2020/6/19 16:06
 */
public class Signal {

    private static volatile int signal = 0;

    static class ThreadA implements Runnable{
        @Override
        public void run() {
            while (signal < 5){
                if (signal % 2 == 0){
                    System.out.println("threadA:" + signal);
                    signal++;
                }
            }
        }
    }

    static class ThreadB implements Runnable{
        @Override
        public void run() {
            while (signal < 5){
                if (signal % 2 == 1){
                    System.out.println("threadB:" + signal);
                    signal = signal + 1;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        new Thread(new ThreadA()).start();
        Thread.sleep(1000);
        new Thread(new ThreadB()).start();
    }
}
