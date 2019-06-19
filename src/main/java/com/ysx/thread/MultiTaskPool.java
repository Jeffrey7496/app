package com.ysx.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/19 10:37
 */
public class MultiTaskPool {
    // 执行任务的线程池
    private static ExecutorService executor = null;
    // 使用单例模式
    // 使用单例模式
    private static class LazyHolder{
        private static final MultiTaskPool INSTANCE = new MultiTaskPool();
    }

    private MultiTaskPool() {
        // 初始化线程池
        executor = Executors.newFixedThreadPool(500);
    }

    //获取实例方法
    public static final MultiTaskPool getInstance(){
        return LazyHolder.INSTANCE;
    }

    // 执行方法
    public void executeJob(Runnable job){
        executor.execute(job);
    }
}

