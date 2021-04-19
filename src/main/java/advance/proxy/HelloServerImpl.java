package advance.proxy;

/**
 * @Author:baifugui
 * @Date：2021-04-18 17:30
 */
public class HelloServerImpl implements HelloServer {
    @Override
    public void sayHell() {
        System.out.println("hello tomoo");
    }

    @Override
    public void goodBye() {
        System.out.println("byebye tomoo");

    }
}
