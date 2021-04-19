package advance.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author:baifugui
 * @Date：2021-04-19 10:00
 */
public class ProxyTest {
    public static void main(String[] args) {
        HelloServerImpl s = new HelloServerImpl();
        InvocationHandler handler = new HelloProxy(s);
        HelloServer server = (HelloServer) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),new Class[]{HelloServer.class},handler);

        server.sayHell();
        server.goodBye();
    }
}
