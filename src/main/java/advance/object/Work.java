package advance.object;

import java.io.Serializable;

/**
 * @autho baifugui
 * @create 2017 11 10 16:24
 */
public class Work implements Cloneable,Serializable{

    private static final long serialVersionUID = 6200961735050975928L;
    private String name;
    private String sex;

    public Work() {
        this.name = "";
        this.sex = "";
    }
    public Work(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

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

    public Work clone(){
        Work work = null;
        try {
            return (Work)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return work;
    }

    @Override
    public String toString() {
        return "Work{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
