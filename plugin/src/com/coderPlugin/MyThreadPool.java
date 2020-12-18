
package com.coderPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建newFixedThreadPool固定大小线程池，线程池中线程数量为5
 */
public class MyThreadPool {
    private static ExecutorService executorService;
    private static boolean statusFlags = false;

    /**
     * 创建一个固定大小线程池
     */
    static {
        executorService = Executors.newFixedThreadPool(8);
        statusFlags = true;
    }

    /**
     * component初始化时调用，相当于预先执行一次静态代码块
     */
    public static void init() {
    }

    /**
     * 获取线程池对象
     * 返回线程池对象时检查线程池是否已经创建
     * @return executorService
     */
    public static ExecutorService getExecutorService() {
        if (statusFlags) {
            return executorService;
        } else {
            MyThreadPool.init();
            return executorService;
        }
    }

    /**
     * 关闭线程池
     */
    public static void close() {
        executorService.shutdown();
    }
}
