package com.zq.thread.volatiles;

/** 双重锁检查实现单例模式
 * @author zhangqing
 * @Package com.zq.util.util.volatiles
 * @date 2020/6/22 15:31
 */
public class DoubleLock {

    /**
     * 不使用volatile关键字
     */
    private static DoubleLock instance;

    /**
     * 双重锁检验
     *
     * 如果这里的变量声明不使用volatile关键字，是可能会发生错误的。它可能会被重排序：
     *
     * instance = new Singleton(); // 第10行
     *
     * // 可以分解为以下三个步骤
     * 1 memory=allocate();// 分配内存 相当于c的malloc
     * 2 ctorInstanc(memory) //初始化对象
     * 3 s=memory //设置s指向刚分配的地址
     *
     * // 上述三个步骤可能会被重排序为 1-3-2，也就是：
     * 1 memory=allocate();// 分配内存 相当于c的malloc
     * 3 s=memory //设置s指向刚分配的地址
     * 2 ctorInstanc(memory) //初始化对象
     *
     *
     * 而一旦假设发生了这样的重排序，比如线程A在第10行执行了步骤1和步骤3，但是步骤2还没有执行完。
     * 这个时候另一个线程B执行到了第7行，它会判定instance不为空，然后直接返回了一个未初始化完成的instance！
     * @return
     */
    public static DoubleLock getInstance(){
        // 第7行
        if (instance == null){
            synchronized (DoubleLock.class){
                if (instance == null){
                    // 第10行
                    instance = new DoubleLock();
                }
            }
        }
        return instance;
    }
}