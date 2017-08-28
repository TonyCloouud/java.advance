package advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Jdk8
 *
 * @autho baifugui
 * @create 2017 08 17 17:53
 */
public class Jdk8 {
    public static   void lambExpression(){
        Arrays.asList( "a", "b", "d" ).forEach(e -> System.out.println(e));
        Arrays.asList( "a", "b", "d" ).forEach((String e) -> System.out.println(e));
        Arrays.asList( "a", "b", "d" ).forEach((String e) -> {
            System.out.println(e);
        });
        System.out.println("===================================");
        List<String> list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
            list.sort((e1,e2) -> e1.compareTo(e2));


    }

    public static void main(String[] args) {
        lambExpression();
    }
}
