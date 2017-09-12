package advance;

import java.util.ArrayList;
import java.util.List;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * @autho baifugui
 * @create 2017 08 21 13:50
 */
public class MemoryAsisTooJconsole {
    private static List<MemoryAsisTooJconsole> tooJconsoles = new ArrayList<>();



    static void  test() throws InterruptedException {
        for (int i = 0;i < 10000;i++){
            MemoryAsisTooJconsole memoryAsisTooJconsole = new MemoryAsisTooJconsole();
            tooJconsoles.add(memoryAsisTooJconsole);
            Thread.sleep(50L);
        }
        getSystemClassLoader();
    }
}
