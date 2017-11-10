package advance.object;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @autho baifugui
 * @create 2017 11 10 16:25
 */
public class ObjetcFactory {
    //new
    public static Work getWorkNew(){
        System.out.println("getWorkNew");
        return new Work();
    }

    //反射机制
    public static Work getWorReflect(){
        System.out.println("getWorReflect");
        Class object = null;
        Work work = null;
        try {
            object = Class.forName("advance.object.Work");
            work = (Work) object.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return work;
    }

    //反射机制-构造器
    public static Work getWorkReflectConstruct(){
        System.out.println("getWorkReflectConstruct");
        Class object = null;
        Work work = null;
        try {
         object = Class.forName("advance.object.Work");
            Constructor constructor = object.getConstructor();
            work = (Work) constructor.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return work;
    }

    //反射机制-带参数构造器
    public static Work getWorkConstructParam(String name,String sex){
        System.out.println("getWorkConstructParam");
        Class objetc = null;
        Work work = null;
        try {
            objetc = Class.forName("advance.object.Work");
            Constructor constructor = objetc.getConstructor(name.getClass(),sex.getClass());
            work = (Work) constructor.newInstance(name,sex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return work;
    }

    //序列化
    public static Work getWorkSerial(String objectPath){
        storeObject2File(objectPath);
        System.out.println("getWorkSerial");
        ObjectInput object = null;
        Work work = null;
        try {
            object = new ObjectInputStream(new FileInputStream(objectPath));
            work = (Work) object.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return work;
    }

    public static void storeObject2File(String objectPath){
        Work work = new Work();
        ObjectOutputStream objectOutputStream = null;
        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectPath));
            objectOutputStream.writeObject(work);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //对象深度拷贝
    public static Work  getWorkClone(){
        System.out.println("getWorkClone");
        Work work = new Work();
        return work.clone();
    }
}
