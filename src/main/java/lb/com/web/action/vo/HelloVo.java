package lb.com.web.action.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by libing on 2016/3/21.
 */
public class HelloVo implements Serializable{

    private String name;
    private int age;
    private Date birthDay;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthDay2;

    private List<Son> sons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getBirthDay2() {
        return birthDay2;
    }

    public void setBirthDay2(Date birthDay2) {
        this.birthDay2 = birthDay2;
    }

    public List<Son> getSons() {
        return sons;
    }

    public void setSons(List<Son> sons) {
        this.sons = sons;
    }

    public static class Son implements Serializable{
        private String name;
        private int age;
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private Date birthDay;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Date getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
        }
    }
}
