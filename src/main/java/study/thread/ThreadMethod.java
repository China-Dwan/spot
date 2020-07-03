package study.thread;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * 测试thread的相关方法
 */
public class ThreadMethod {


    /**
     * 常用方法演示
     * start(): 开始执行该线程
     * run():  直接执行线程的run()方法, 调用start()时也会运行run()方法
     * stop(): 强制结束该线程执行，不推荐使用
     */
    public static void main(String[] args) throws InterruptedException {
        joinM();
    }


    /**
     * wait  释放锁
     * 线程进入等待状态,同时释放对象锁,这个方法是object的方法
     */
    static class WaitM implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            m();
        }

        private synchronized void m() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + "执行");
            if (Thread.currentThread().getName().equals("1")) {
                System.out.println(Thread.currentThread().getName() + "wait");
                //毫秒
                this.wait(5000);
            }
            System.out.println(Thread.currentThread().getName() + "执行完成");
        }
    }
    public static void waitM() throws InterruptedException {
        WaitM m = new WaitM();
        new Thread(m, "1").start();
        TimeUnit.SECONDS.sleep(2);
        new Thread(m, "2").start();
    }


    /**
     * sleep  不释放锁
     * 线程进入等待状态,不释放对象锁
     */
    static class SleepM implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            m();
        }

        private synchronized void m() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + "执行");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "执行完成");
        }
    }
    public static void sleepM() throws InterruptedException {
        SleepM m = new SleepM();
        new Thread(m, "1").start();
        new Thread(m, "2").start();
    }


    /**
     * yield
     * 暂停当前正执行的线程对象，让当前线程变为可运行状态，当然这个线程仍然会再次执行，目的是让相同优先级的线程之间能够适当的轮转执行
     */
    static class YieldM implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (i==5) {
                    System.out.println(Thread.currentThread().getName() + "进入提示");
                    Thread.yield();
                    System.out.println(Thread.currentThread().getName() + "继续运行");
                }
                System.out.println(Thread.currentThread().getName() + "执行" + i);
            }
        }
    }
    public static void yieldM() {
        YieldM m = new YieldM();
        new Thread(m, "1").start();
        new Thread(m, "2").start();
    }

    /**
     * join  谁调用，暂停谁
     * 如在主线程中调用a线程的join()方法，则暂停主线程，等待a线程执行完毕后，主线程继续向下执行
     * 当我们想保证线程执行顺序的时候，就可以使用join()
     */
    static class JoinM implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "执行" + i);
            }
        }
    }
    public static void joinM() throws InterruptedException {
        JoinM m = new JoinM();
        Thread t1 = new Thread(m, "1");
        t1.start();
        t1.join();
        new Thread(m, "2").start();
    }
}
