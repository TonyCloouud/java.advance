package advance.object;

/**
 * @autho baifugui
 * @create 2017 11 10 17:15
 */
public class Client {
    public static void main(String[] args) {
        ObjetcFactory.getWorkNew();
        ObjetcFactory.getWorReflect();
        ObjetcFactory.getWorkReflectConstruct();
        ObjetcFactory.getWorkConstructParam("tom","boy");
        ObjetcFactory.getWorkSerial("C:\\myfile\\os_source\\comprehensive.advance\\src\\main\\java\\advance\\object\\lou.dat");
        ObjetcFactory.getWorkClone();
    }
}
