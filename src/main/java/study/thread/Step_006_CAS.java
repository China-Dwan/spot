package study.thread;

import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Step_006_CAS {

    /**
     * atomic 用CAS来保证线程安全
     */
    public static void main(String[] args) throws Exception {
//        atomic();
        Unsafe unsafe = Unsafe.getUnsafe();
//        unsafe.compareAndSwapInt()
    }

    public static void atomic() throws Exception {
        // 监控所有线程是否已经执行完毕
        CountDownLatch downLatch = new CountDownLatch(100000);
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> {
                count.incrementAndGet();
                downLatch.countDown();
            }).start();
        }

        // 所有线程执行完毕后，才会继续向下执行
        downLatch.await();
        System.out.println(count.get());
    }
}
