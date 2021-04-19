package advance.rpc;

/**
 * @Author:baifugui
 * @Date：2021-04-18 18:29
 */
public class HelloServiceImpl implements  HelloService{
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

}
