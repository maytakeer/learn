package com.zq.thread.concurrents;

import java.util.concurrent.Phaser;

/** 主线程等待这些工作线程结束，除了使用Thread.join()之外，也可以尝试以下的方式：
 * @author zhangqing
 * @Package com.zq.thread.concurrents
 * @date 2020/7/1 15:11
 */
public class PhaserTest4 {

    public static void main(String[] args) {
        final int count = 5;
        final int phaseToTerminate = 3;
        final Phaser phaser = new Phaser(count){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====" + phase + "=====");
                return phase == phaseToTerminate || registeredParties == 0;
            }
        };

        for (int i = 0; i < count; i++){
            System.out.println("starting thread, id:" + i);
            final Thread thread = new Thread(new Task(i, phaser));
            thread.start();
        }

        int register = phaser.register();
        System.out.println("**********" + register);
        while (!phaser.isTerminated()){
            phaser.arriveAndAwaitAdvance();
        }
        System.out.println("done");
    }

    public static class Task implements Runnable {

        private final int id;

        private final Phaser phaser;

        public Task(int id, Phaser phaser){
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            while (!phaser.isTerminated()){
                try{
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id:" + this.id);
                phaser.arriveAndAwaitAdvance();
            }
        }
    }
}
