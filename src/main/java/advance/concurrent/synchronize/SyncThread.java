package advance.concurrent.synchronize;


/**
 * 同步线程
 */
public class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
        count = 0;
    }

    public  void run() {
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount() {
        return count;
    }
//    public static void main(String[] args) {
//        Account account = new Account("amount", 100.0f);
//        AccountOperator accountOperator = new AccountOperator(account);
//        final int THREAD_NUM = 5;
//        Thread threads[] = new Thread[THREAD_NUM];
//        for (int i = 0; i < 10; i++) {
//            threads[i] = new Thread(accountOperator, "a" + i);
//            threads[i].start();
//        }
//
//        synchronized(TestClass.class){
//
//        }
//    }
}