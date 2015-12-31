package lb.com.hessian;

import lb.com.hessian.vo.PersonVo;

/**
 * Created by libing on 2015/12/29.
 */
public interface HessianTsService {

    public String hello(String name);

    public PersonVo findMonitor(int classLvl);

}
