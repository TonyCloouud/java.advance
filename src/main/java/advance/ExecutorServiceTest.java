package advance;

import advance.util.HttpUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * ExecutorService
 *
 * @autho baifugui
 * @create 2017 08 23 14:32
 */
public class ExecutorServiceTest {
    static void log(String msg){
        System.out.println(System.currentTimeMillis() + " -> " + msg);
    }

    static int getThreadPoolRunState(ThreadPoolExecutor pool) throws IllegalAccessException, NoSuchFieldException {
        Field f = ThreadPoolExecutor.class.getDeclaredField("runState");
        f.setAccessible(true);
        int v = f.getInt(pool);
        return v;
    }


    static void executorTest() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
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


    static  void jdwxApitest() throws InterruptedException {
        long start = System.currentTimeMillis();
        String url = "http://localhost:8080/user/channel/validJdwxAccount";
        Map<String, String> params = new HashMap<>();
        params.put("userId","test_yong");
        params.put("dataId","11679");
        params.put("channel","JDWX");
        params.put("token","openApiJdwx");
        ExecutorService es = Executors.newFixedThreadPool(10);

        for(int i = 0;i <100;i++){
            Map<String,String> params1 = new HashMap<>();
            params1.put("dataId","11679");
            params1.put("channel","JDWX");
            params1.put("token","openApiJdwx");
            params1.put("userId","aaa"+i);
            es.submit(new Runnable() {
                @Override
                public void run() {
                    HttpUtil.onLinkNetPost(url,params1);
                }
            });
        }

    }

    public static void main(String[] args) throws InterruptedException {
        jdwxApitest();
    }


}
