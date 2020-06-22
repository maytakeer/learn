package com.zq.thread.group;

/** 如果某个线程的优先级大于线程所在线程组的最大优先级，该线程的优先级会失效，优先级为线程组的最大优先级
 * @author zhangqing
 * @Package com.zq.thread.group
 * @date 2020/6/19 10:05
 */
public class GroupPriorityDemo {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("t1");
        threadGroup.setMaxPriority(6);
        Thread thread = new Thread(threadGroup, "thread");
        thread.setPriority(9);
        System.out.println("我是线程组的优先级" + threadGroup.getMaxPriority());
        System.out.println("我是线程的优先级" + thread.getPriority());
    }
}
