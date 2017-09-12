package advance;

import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMerory
 *
 * @autho baifugui
 * @create 2017 08 16 18:18
 */
public class OutOfMerory {
    private static  List<OutOfMerory> list = new ArrayList();
    public static  void add(){
        while(true){
            OutOfMerory OutOfMerory = new OutOfMerory();
            list.add(OutOfMerory);
        }

    }

}
