package study.thread;

/**
 * 线程状态
 */
public class Step_002_ThreadStatus {

    /**
     * NEW
     * 新建线程对象,没有调用start()方法
     *
     * RUNNABLE
     * 调用start()方法之后线程就进入了可运行状态，这个状态又可细分为ready和running两个状态
     * ready到running两个状态是由系统CPU调度的，可以使用Thread.yield()方法，由running变为ready
     *
     * WAITING
     * 调用wait() join()方法后，变为等待状态，需要notify() notifyAll()来唤醒wait()，唤醒后变为RUNNABLE状态
     *
     * TIMED_WAITING
     * 调用wait(long) sleep(long) join(long)变为超时等待状态，可以使用notify() notifyAll()来唤醒wait(long)，唤醒后变为RUNNABLE状态
     *
     * BLOCKED
     * 等待进入synchronized()块或者方法，当获取到锁的时候，变为RUNNABLE状态
     *
     * TERMINATED
     * 正常执行完成或者异常终止
     */
}
