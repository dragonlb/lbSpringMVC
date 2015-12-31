package lb.com.hessian.vo;

import java.io.Serializable;

/**
 * Created by libing on 2015/12/29.
 */
public class PersonVo implements Serializable {

    /**
     */
    private static final long serialVersionUID = -2629775889876718915L;

    private String name;

    private String address;

    private int age;

    private SEX sex;

    private String enName;

    public PersonVo() {
    }

    public PersonVo(String name, int age, SEX sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SEX getSex() {
        return sex;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String toJSON(){
        return "name:"+this.getName()+"," +
                "address:"+this.getAddress()+"," +
                "age:"+this.getAge()+"," +
//                "sex:"+this.getSex().getDesp()+
                "enName:"+this.getEnName()+"," +
                "";
    }

    public static enum SEX{
        MALE("男"),FEMALE("女");
        private final String desp;

        SEX(String pDesp){
            this.desp = pDesp;
        }

        public String getDesp() {
            return desp;
        }

        public String toString(){
            return this.getDesp();
        }
    }
}
