package com.zq.thread.concurrents;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Phaser;

/** 在某些外部条件满足时，才真正开始任务的执行
 * @author zhangqing
 * @Package com.zq.thread.concurrents
 * @date 2020/7/1 14:38
 */
public class PhaserTest2 {

    public static void main(String[] args) throws Exception {
        final Phaser phaser = new Phaser(1);
        for (int i = 0; i < 5; i++){
            phaser.register();
            System.out.println("starting thread, id:" + i);
            final Thread thread = new Thread(new Task(i, phaser));
            thread.start();
        }

        System.out.println("Press ENTER to continue");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
        phaser.arriveAndDeregister();

    }

    public static class Task implements Runnable{

        private final int id;

        private final Phaser phaser;

        public Task(int id, Phaser phaser){
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();
            System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);
        }
    }
}
