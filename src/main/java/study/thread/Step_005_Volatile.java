package study.thread;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * volatile关键字
 */
public class Step_005_Volatile {

    /**
     * 三个特性
     * 1，保证线程可见性
     * 2，禁止指令重排序
     * 3，无法保证原子性
     */

    public static void main(String[] args) throws Exception {
        testVolatile3();
    }


    /**
     * 1，保证线程可见性
     * 对于jvm堆内存来说，堆内存创建一个共享对象，当线程使用到这个对象时，就会将对象信息copy到线程自己的工作内存中，这样就导致本线程运行
     * 过程中如果其他线程改变了这个对象信息，当前运行线程是感知不到的，加上volatile就是线程每次使用这个对象时都会从内存中读取最新的值
     */
    boolean b = true;
    //    volatile boolean b = true;
    public static void testVolatile1() {
        Step_005_Volatile t = new Step_005_Volatile();
        new Thread(t::m).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.b = false;
    }
    public void m() {
        System.out.println("start");
        while (b) {
        }
        System.out.println("end");
    }


    /**
     * 2，禁止指令重排序验证
     * 创建一个对象分为很多部分，了解到的  1分配内存空间  2初始化对象  3将内存空间地址赋值给对应的引用
     * 因为CPU执行时，会自动对代码执行顺序进行优化，执行顺序可能为1-3-2
     * 这样当极端情况出现时，就会A线程进入判断是空，获得锁，开始创建对象，创建对象顺序为1-3-2，B线程同时执行，就会发现对象不是空，直接拿到未初始化的对象，这样就是错误的
     * 而加上volatile后，就保证对象创建的执行顺序，其他代码执行过程不会发生错误
     */
    static class SS {
        //        private static SS ss;
        private volatile static SS ss;

        public SS() {
        }

        public static SS getInstance() {
            if (ss == null) {
                synchronized (SS.class) {
                    if (ss==null) {
                        ss = new SS();
                    }
                }
            }

            return ss;
        }
    }


    /**
     * 3，无法保证原子性
     * 虽然保证了可见性，但是多线程情况下，每个线程创建时拿到的有可能是相同的值，+1操作后同样会发生值覆盖操作
     */
    volatile int count = 0;
    public static void testVolatile3() throws InterruptedException {
        Step_005_Volatile t2 = new Step_005_Volatile();

        // 保证list的add操作不会异常
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch downLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                t2.count++;
                list.add(t2.count);
                downLatch.countDown();
            }).start();
        }
        downLatch.await();

        Integer integer = list.stream().max(Integer::compareTo).orElse(0);
        System.out.println(integer);
    }
}
