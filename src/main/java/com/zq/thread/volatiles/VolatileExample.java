package com.zq.thread.volatiles;

/** 内存可见性
 * 线程A先自行方法writer方法，线程B后执行reader方法
 * a = 1 flag = true
 * flag不用volatile修饰
 * a = 0 flag = false
 * @author zhangqing
 * @Package com.zq.util.util.volatiles
 * @date 2020/6/22 9:17
 */
public class VolatileExample {
    int a = 0;
    volatile boolean flag = false;

    public void writer(){
        //step 1
        a = 1;
        //step 2
        flag = true;
    }

    public void reader(){
        //step 3
        if (flag){
            //step 4
            System.out.println(a);
        }
    }
}
