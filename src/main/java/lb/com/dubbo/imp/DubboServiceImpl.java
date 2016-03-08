package lb.com.dubbo.imp;

import lb.com.dubbo.DubboService;
import org.springframework.stereotype.Service;

/**
 * Created by libing on 2016/3/7.
 */
@Service("dubboService")
public class DubboServiceImpl implements DubboService{

    @Override
    public String sayHello(String name) {
        System.out.println("Server received "+name);
        return "Hello "+name;
    }
}
