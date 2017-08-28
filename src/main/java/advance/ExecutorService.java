package advance;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService
 *
 * @autho baifugui
 * @create 2017 08 23 14:32
 */
public class ExecutorService {
    static void log(String msg){
        System.out.println(System.currentTimeMillis() + " -> " + msg);
    }

    static int getThreadPoolRunState(ThreadPoolExecutor pool) throws IllegalAccessException, NoSuchFieldException {
        Field f = ThreadPoolExecutor.class.getDeclaredField("runState");
        f.setAccessible(true);
        int v = f.getInt(pool);
        return v;
    }

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,2220,0, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1));
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 1000; i++) {
            final int index = i;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    log("run task:" + index + " -> " +Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log("run over:" + index + " -> "+Thread.currentThread().getName());
                }
            });
        }
        Executors.newSingleThreadExecutor();
        Executors.newFixedThreadPool(1);
        Executors.newCachedThreadPool();
        log("before sleep");
        Thread.sleep(4000L);
        log("before shutdown");
//        pool.shutdownNow();
        log("after shutdown ,poo.isTerminated="+pool.isTerminated());
        pool.awaitTermination(1000L,TimeUnit.SECONDS);
        log("now,pool.isTerminated="+pool.isTerminated()+",state="+getThreadPoolRunState(pool));
    }
}
