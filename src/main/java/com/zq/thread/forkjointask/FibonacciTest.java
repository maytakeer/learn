package com.zq.thread.forkjointask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 斐波那契数列数列是一个线性递推数列，从第三项开始，每一项的值都等于前两项之和：
 *
 * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89······
 *
 * 如果设f(n）为该数列的第n项（n∈N*），那么有：f(n) = f(n-1) + f(n-2)。
 *
 * @author zhangqing
 * @Package com.zq.thread.forkjointask
 * @date 2020/7/1 15:49
 */
public class FibonacciTest {
    private static class Fibonacci extends RecursiveTask<Integer>{

        int n;

        public Fibonacci(int n){
            this.n = n;
        }

        /**
         * 主要实现逻辑都在compute（）里
         * @return
         */
        @Override
        protected Integer compute() {
            // 这里先假设 n >= 0
            if (n <= 1){
                return n;
            }else {
                // f(n-1)
                Fibonacci f1 = new Fibonacci(n - 1);
                f1.fork();
                // f(n-2)
                Fibonacci f2 = new Fibonacci(n - 2);
                f2.fork();
                // f(n) = f(n - 1) + f(n - 2)
                return f1.join() + f2.join();
            }
        }
    }

    // 普通递归，复杂度为O(2^n)
    private static Integer plainRecursion(int n){
        if ( n <= 1){
            return n;
        }else {
            Integer n1 = plainRecursion(n - 1);
            Integer n2 = plainRecursion(n - 2);
            return n1 + n2;
        }
    }

    // 通过循环来计算，复杂度为O(n)
    private static int computeFibonacci(int n){
        // 假设n >= 0
        if (n <= 1){
            return 1;
        }else {
            int first = 1;
            int second = 1;
            int third = 0;
            for (int i = 3; i <= n; i++){
                third = first + second;

                first = second;
                second = third;
            }
            return third;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println("CPU核数：" + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        Fibonacci fibonacci = new Fibonacci(40);
        Future<Integer> future = forkJoinPool.submit(fibonacci);
        System.out.println(future.get());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
//        long start = System.currentTimeMillis();
//        Integer result = plainRecursion(40);
//        System.out.println(result);
//        long end = System.currentTimeMillis();
//        System.out.println("耗时：" + (end - start) + "ms");
        int i = computeFibonacci(40);
        System.out.println(i);
    }
}
