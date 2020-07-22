package study.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * volatile关键字
 */
public class VolatileTest {

    /**
     * 三个特性
     * 1，保证线程可见性
     * 2，禁止指令重排序
     * 3，无法保证原子性
     */

    public static void main(String[] args) {

    }


    /**
     * 保证线程可见性
     * 对于jvm堆内存来说，堆内存创建一个共享对象，当线程使用到这个对象时，就会将对象信息copy到线程自己的工作内存中，这样就导致本线程运行
     * 过程中如果其他线程改变了这个对象信息，当前运行线程是感知不到的，加上volatile就是线程每次使用这个对象时都会从内存中读取最新的值
     */
    boolean b = true;
    //    volatile boolean b = true;
    public static void testVolatile() {
        VolatileTest t = new VolatileTest();
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
     * 禁止指令重排序验证
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


    // 无法保证原子性
    volatile int count = 0;
    public static void testVolatile1() {
        VolatileTest t2 = new VolatileTest();

        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        Integer integer1 = integers.stream().max(Integer::compareTo).orElse(0);
        System.out.println(integer1);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                t2.count++;
                list.add(t2.count);
            }).start();
        }


        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer integer = list.stream().max(Integer::compareTo).orElse(0);
        System.out.println(integer);
    }
}
