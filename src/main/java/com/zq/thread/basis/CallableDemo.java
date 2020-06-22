package com.zq.thread.basis;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhangqing
 * @Package com.zq.thread.basis
 * @date 2020/6/18 17:18
 */
public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        //模拟计算需要一秒
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //使用
        ExecutorService executor = Executors.newCachedThreadPool();
        CallableDemo callableDemo = new CallableDemo();
        Future<Integer> result = executor.submit(callableDemo);
        // 注意调用get方法会阻塞当前线程，直到得到结果。
        // 所以实际编码中建议使用可以设置超时时间的重载get方法。
        System.out.println(result.get());
    }
}
