package advance.concurrent.multThread;

/**
 * @autho baifugui
 * @create 2017 10 19 17:45
 */
public class JoinDemo {

    public static void main(String[] args) {
        Father father = new Father();
        father.start();
    }

}

class Father extends Thread{
    @Override
    public void run(){
        System.out.println("start run Father");
        Son son = new Son();
        son.start();
        try {
            son.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Father doing end");
    }
}

class Son extends Thread{
    @Override
    public void run(){
        System.out.println("Son doing something");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
