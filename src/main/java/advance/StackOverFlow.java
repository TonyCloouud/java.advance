package advance;

/**
 * StackOverFlow
 *
 * @autho baifugui
 * @create 2017 08 16 10:31
 */
public class StackOverFlow {
    private static int stackLength = 0;
    public void stackOverFlow(){
        stackLength++;
        stackOverFlow();
    }

    public static void main(String[] args) throws Throwable {
        StackOverFlow stackOverFlow = new StackOverFlow();
        try {
            stackOverFlow.stackOverFlow();
        }catch (Throwable e){
            System.out.println("stackLength:"+stackLength);
            throw e;
        }

    }
}
