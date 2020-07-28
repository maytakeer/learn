package com.zq.thread.scheduledThreadPoolExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangqing
 * @Package com.zq.thread.scheduledThreadPoolExecutor
 * @date 2020/7/2 11:51
 */
public class ThreadPool {
    private static final ScheduledThreadPoolExecutor executor = new
            ScheduledThreadPoolExecutor(1, Executors.defaultThreadFactory());
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        // 新建一个固定延迟时间的计划任务
//        executor.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                if (haveMsgAtCurrentTime()){
//                    System.out.println(sdf.format(new Date()));
//                    System.out.println("大家注意了，我要发消息了");
//                }
//            }
//        }, 1, 1, TimeUnit.SECONDS);

//        executor.scheduleWithFixedDelay(new TestThread(), 1, 10, TimeUnit.SECONDS);
        for (int i = 0; i < 100; i++ ){
            executor.schedule(new TestThread(), 10, TimeUnit.SECONDS);
        }
    }

    public static boolean haveMsgAtCurrentTime(){
        //查询数据库，有没有当前时间需要发送的消息
        //这里省略实现，直接返回true
        return true;
    }

    public void test(){
        Timer timer = new Timer();
//        timer.schedule();
    }

    static class TestThread extends TimerTask{
        @Override
        public void run() {
            System.out.println("大家注意了，我要发消息了");
        }
    }
}
