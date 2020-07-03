package study.thread;

public class SynchronizedTest {

    /**
     * 使用场景
     * public sync void method() 对当前对象this加锁
     * public static sync void method() 对当前类的class对象加锁
     * public void method() { sync(obj) } 修饰代码块，指定一个加锁的对象，给对象加锁，不能使用string和int,long常量等基础数据类型
     */

    /**
     * 底层实现
     * 对象被创建在堆中，存储布局方式分为，对象头，实例数据，对齐填充，其中对象头便是主要角色，
     * 对象头来说主要包括两部分，一，自身运行时的数据比如，锁状态标志，持有锁线程ID，偏向线程ID等，这部分内容被称为mark word；二，类型指针，jvm通过这个指针来确定这个对象是哪个类的实例
     */

    /**
     * 锁升级概念
     * 无锁->偏向锁->自旋锁->重量级锁
     *
     * 一个对象刚开始实例化的时候，没线程来访问，此时是可偏向的，他现在认为只有一个线程能来访问；当第一个线程来访问时，会偏向这个线程，
     * 此时对象持有偏向锁，偏向第一个线程，修改对象头成为偏向锁时使用CAS操作，并将对象头中的线程ID改成自己的id，之后再访问这个对象时就只需要对比id，不需要进行CAS操作；
     *
     * 一旦有第二个线程访问这个对象，因为偏向锁不会主动释放，所以第二个线程可以看到对象是偏向状态，这是表明这个对象上已经存在竞争了，
     * 检查原来持有该对象锁的线程是否依然存活，如果挂了，则可以将对象变为无锁状态，然后重新偏向新的线程，如果原来的线程依然存活，就马上执行那个线程的操作栈，
     * 检查该对象的使用情况，如果仍然需要持有偏向锁，则偏向锁升级为自旋锁，如果不需要继续使用，则将对象恢复为无锁状态，然后重新偏向；
     *
     * 一般情况下两个线程对于同一个锁的操作都会错开，或者自旋一下，另一个线程就会释放锁，但是当自旋超过一定的次数，或者一个线程持有锁，
     * 一个在自旋，又来了第三个线程，则自旋锁升级成为重量级锁，除了拥有锁的线程之外，所有线程处于等待状态
     */

    /**
     * 可重入锁：sync method() 与 sync (this)：并无太大区别，
     * 类方法  用static修饰的方法
     * 实例方法：当一个类创建了一个对象后，这个对象就可以调用这个类的方法
     * 当方法属于实例方法时，两个实例，方法访问不冲突；
     * 当方法属于类方法时，两个实例，方法访问冲突，当只有一个实例时，无论是实例方法还是类方法，都不能同时访问
     */

    /**
     * synchronized与lock区别
     * 两个层面：前者是Java的关键字,在jvm层面上,后者是一个类
     * 锁释放不同：前者获取锁的线程执行完成后,释放锁,线程执行发生异常,jvm会让线程释放锁，后者在finally中必须释放锁,不然容易造成线程死锁
     * 锁的获取不同：前者A线程获取锁,B线程等待,如果A线程阻塞,则B线程一直等待，后者lock有多个锁获取的方式,大致就是可以尝试获得锁,线程可以不用一直等待
     * 锁状态：前者无法判断,后者可以判断
     * 锁类型：前者,可重入 不可中断 非公平,  后者,可重入 可判断 可公平
     * 性能：前者适用少量同步,后者适用大量同步
     */
}
