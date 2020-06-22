package com.zq.thread.group;

import java.util.stream.IntStream;

/** 线程优先级测试
 *  不建议在项目中使用，不可靠
 *  java对线程设置的优先级只是给操作系统的建议，不一定会执行。真正的调用顺序，是有操作系统的线程调度机制算法决定的
 *  优先级高只能表示先执行的概率更高
 * @author zhangqing
 * @Package com.zq.thread.group
 * @date 2020/6/19 9:55
 */
public class PriorityTestDemo {

    public static class T1 extends Thread{
        @Override
        public void run() {
            super.run();
            System.out.println(String.format("当前执行的线程是: %s, 优先级: %d", Thread.currentThread().getName(), Thread.currentThread().getPriority()));
        }
    }

    public static void main(String[] args) {
        IntStream.range(1,10).forEach(i -> {
            Thread thread = new Thread(new T1());
            thread.setPriority(i);
            thread.start();
        });
    }
}
