package com.zq.thread.group;

/** 优先级
 * @author zhangqing
 * @Package com.zq.thread.group
 * @date 2020/6/19 9:53
 */
public class PriorityDemo {
    public static void main(String[] args) {
        Thread a = new Thread();
        System.out.println("我是默认线程优先级："+a.getPriority());
        Thread b = new Thread();
        b.setPriority(10);
        System.out.println("我是设置过的线程优先级："+b.getPriority());
    }
}
