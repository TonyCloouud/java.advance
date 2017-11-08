package advance.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @autho baifugui
 * @create 2017 10 26 14:57
 */
public class Atomic {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.compareAndSet(1,1);
    }
}
