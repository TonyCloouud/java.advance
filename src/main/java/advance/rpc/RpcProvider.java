package advance.rpc;

/**
 * @Author:baifugui
 * @Date：2021-04-18 18:30
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }
}
