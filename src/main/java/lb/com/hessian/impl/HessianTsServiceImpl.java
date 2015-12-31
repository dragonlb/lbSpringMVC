package lb.com.hessian.impl;

import lb.com.hessian.HessianTsService;
import lb.com.hessian.vo.PersonVo;

/**
 * Created by libing on 2015/12/29.
 */
public class HessianTsServiceImpl implements HessianTsService{

    public String hello(String name){
        return "Hello,"+name+" !Welcome to hessian world.";
    }

    public PersonVo findMonitor(int classLvl){
        PersonVo retVo = new PersonVo();
        if(classLvl<=2) {
            retVo.setName("张三");
            retVo.setEnName("zhangsan");
            retVo.setAge(10);
            retVo.setSex(PersonVo.SEX.FEMALE);
            retVo.setAddress("上海田林");
        }
        else{
            retVo.setName("李四");
            retVo.setEnName("lisi");
            retVo.setAge(11);
            retVo.setSex(PersonVo.SEX.FEMALE);
            retVo.setAddress("上海松江");
        }
        return retVo;
    }
}
