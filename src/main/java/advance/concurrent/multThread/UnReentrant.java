package advance.concurrent.multThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @autho baifugui
 * @create 2017 10 19 16:10
 */
public class UnReentrant{
   private static Lock lock = new ReentrantLock();
    public static void outer(){
        lock.lock();
        System.out.println("outer");
        inner();

        lock.unlock();
    }
    public static void inner(){
        lock.lock();
        System.out.println("inner");
        lock.unlock();
    }

    public static void main(String[] args) {
        outer();
    }


}