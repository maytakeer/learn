package com.zq.thread.concurrents;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** Semaphore 限制线程的数量
 * @author zhangqing
 * @Package com.zq.thread.concurrents
 * @date 2020/6/30 15:15
 */
public class SemaphoreDemo {
    static class MyThread implements Runnable{

        private int value;

        private Semaphore semaphore;

        public MyThread(int value, Semaphore semaphore){
            this.value = value;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try{
                //获取permit
                semaphore.acquire();
                System.out.println(String.format("当前线程是%d，还剩%d个资源，还有%d个线程在等待",value, semaphore.availablePermits(), semaphore.getQueueLength()));
                // 睡眠随机时间，打乱释放顺序
                Random random = new Random();
                Thread.sleep(random.nextInt(1000));
                System.out.println(String.format("线程%d释放了资源", value));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                // 释放permit
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++){{
            new Thread(new MyThread(i, semaphore)).start();
        }}
        System.out.println("******");
    }
}
