package com.business.cancelorder;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    //核心线程数
    private final static int corePoolSize = 20;
    //最大线程数
    private final static int maximumPoolSize = 20;
    //存活时间
    //线程池中线程等待工作的超时时间    TimeUnit.MILLISECONDS毫秒
    private final static long keepAliveTime = 1000l;

    public static ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.MILLISECONDS,new SynchronousQueue<>());
    }
}
