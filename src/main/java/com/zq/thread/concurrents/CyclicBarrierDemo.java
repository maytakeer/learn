package com.zq.thread.concurrents;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/** 作用跟CountDownLatch类似，但是可以重复使用
 * @author zhangqing
 * @Package com.zq.thread.concurrents
 * @date 2020/7/1 13:43
 */
public class CyclicBarrierDemo {

    static class PreTaskThread implements Runnable {

        private String task;

        private CyclicBarrier cyclicBarrier;

        private PreTaskThread(String task, CyclicBarrier cyclicBarrier){
            this.task = task;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            // 假设总共三个关卡
            for (int i = 1; i < 5; i++){
                try{
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000));
                    System.out.println(String.format("关卡%d的任务%s完成", i, task));
                    cyclicBarrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                // 重置屏障
                cyclicBarrier.reset();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("本关卡所有前置任务完成，开始游戏...");
        });

        new Thread(new PreTaskThread("加载地图数据", cyclicBarrier)).start();
        new Thread(new PreTaskThread("加载人物模型", cyclicBarrier)).start();
        new Thread(new PreTaskThread("加载背景音乐", cyclicBarrier)).start();
    }
}
