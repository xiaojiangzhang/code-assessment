
package com.coderPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建newFixedThreadPool固定大小线程池，线程池中线程数量为5
 */
public class MyThreadPool {
    private static ExecutorService executorService;

    /**
     * 创建一个固定大小线程池
     */
    static {
        executorService = Executors.newFixedThreadPool(8);
    }

    /**
     * component初始化时调用，相当于预先执行一次静态代码块
     */
    public static void init() {
    }

    /**
     * 获取线程池对象
     *
     * @return executorService
     */
    public static ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * 关闭线程池
     */
    public static void close() {
        executorService.shutdown();
    }
}
