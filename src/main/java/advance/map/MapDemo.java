package advance.map;

import java.util.*;

/**
 * @autho baifugui
 * @create 2017 10 30 15:14
 */
public class MapDemo {
    private static String ss = "hel";

    public static void main(String[] args) {
        String loc = "";
        Demo demo = new Demo();

        printl(demo,loc);
        System.out.println(demo.toString()+"|"+loc);

    }

    public static  void print(int n){
        System.out.println(Integer.toBinaryString(n));
    }

    public  static void printl(Demo demo,String loc){
        demo.setName("join"); ;
        demo.setSex("sex");
        loc = "tom";
    }

    static final int hash(Object key) {   //jdk1.8 & jdk1.7
        int h;
        // h = key.hashCode() 为第一步 取hashCode值
        // h ^ (h >>> 16)  为第二步 高位参与运算
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    static int indexFor(int h, int length) {  //jdk1.7的源码，jdk1.8没有这个方法，但是实现原理一样的
        return h & (length-1);  //第三步 取模运算
    }

}
class Demo{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    String name;
    String sex;

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
