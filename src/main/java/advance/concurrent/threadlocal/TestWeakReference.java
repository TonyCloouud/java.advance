package advance.concurrent.threadlocal;

import java.lang.ref.WeakReference;

/**
 * @autho baifugui
 * @create 2017 10 19 11:08
 */
public class TestWeakReference {
    public static void main(String[] args) {
        Car car = new Car("Tom","男");
        WeakReference<Car> weakReference = new WeakReference<Car>(car);
        int i = 0;
        while (true){
            System.out.println("this is strong referecne ,car will not be GC"+car);
            i++;
            if(weakReference.get() != null){
                System.out.println("object is alive during "+i+" loop "+weakReference.get().toString());
            }else {
                System.out.println("object is die");
                break;
            }
        }
    }
}

class Car{
    private String name;

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    private String sex;
    public Car(String name,String sex){
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
