package lb.dubbo.imp;

import lb.dubbo.DubboService;

/**
 * Created by libing on 2016/3/7.
 */
public class DubboServiceImpl implements DubboService{

    @Override
    public String sayHello(String name) {
        System.out.println("Server received "+name);
        return "Hello "+name;
    }
}
