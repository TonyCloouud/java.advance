package advance.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author:baifugui
 * @Date：2021-04-18 17:31
 */
public class HelloProxy implements InvocationHandler {
    private Object realObject;
    public HelloProxy(Object o){
        this.realObject = o;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object s = method.invoke(realObject,args);
        System.out.println("after invoke");

        return s;
    }
}
