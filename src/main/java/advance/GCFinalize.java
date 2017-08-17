package advance;

/***
 * 执行finalize()的是一个低优先级的线程，既然是一个新的线程，虽然优先级低了点，
 * 但也是和垃圾收集器并发执行的，所以垃圾收集器没必要等这个低优先级的线程执行完才继续执行。
 * 也就是说，finalize()方法不一定会在对象第一次标记后执行。
 * 用一句清晰易懂的话来说就是：虚拟机确实有调用方法的动作，但是不会确保在什么时候执行完成。
 * 因此也就出现了上面输出的结果，对象被回收之后，那个低优先级的线程才执行完
 */
public class GCFinalize {
    private static  GCFinalize gcFinalize = null;
    public static void testGC() throws InterruptedException {
        gcFinalize = null;
        //通知垃圾收集器回收对象
        System.gc();
//让低优先级线程先运行，finalize方法
        Thread.sleep(2000L);

        if(gcFinalize == null){
            System.out.println("我挂了");
        }else {
            System.out.println("我还活着");
        }

    }
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        gcFinalize = this;//将对象重新加入关系网
        System.out.println("我被调用了");
    }

    public static void main(String[] args) throws InterruptedException {
         gcFinalize = new GCFinalize();
        testGC();
        testGC();

    }
}
