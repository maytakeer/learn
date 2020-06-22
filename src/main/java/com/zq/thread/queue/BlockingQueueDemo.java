package com.zq.thread.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**生产者-消费者模型
 * @author zhangqing
 * @Package com.zq.thread.queue
 * @date 2020/6/22 17:03
 */
public class BlockingQueueDemo {

    private int queueSize = 10;

    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueSize);

    public static void main(String[] args) {
        BlockingQueueDemo blockingQueueDemo = new BlockingQueueDemo();

        Producer producer = blockingQueueDemo.new Producer();
        Consumer consumer = blockingQueueDemo.new Consumer();

        producer.start();
        consumer.start();
    }

    class Consumer extends Thread{
        @Override
        public void run() {
            consume();
        }

        private void consume(){
            while (true){
                try{
                    queue.take();
                    System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                }catch (Exception e){

                }
            }
        }
    }

    class Producer extends Thread{
        @Override
        public void run() {
            produce();
        }

        private void produce(){
            while (true){
                try{
                    queue.put(1);
                    System.out.println("向队列取中插入一个元素，队列剩余空间："+(queueSize-queue.size()));
                }catch (Exception e){

                }
            }
        }
    }
}
