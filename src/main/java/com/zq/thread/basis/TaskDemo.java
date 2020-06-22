package com.zq.thread.basis;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author zhangqing
 * @Package com.zq.thread.basis
 * @date 2020/6/18 17:31
 */
public class TaskDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        //模拟计算需要一秒
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //使用
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = new FutureTask<>(new TaskDemo());
        executor.submit(futureTask);
        System.out.println(futureTask.get());
    }
}
