package lbts.com.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import lb.com.hessian.HessianTsService;
import lb.com.hessian.vo.PersonVo;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * Created by libing on 2015/12/29.
 */
public class HessianTs {

    @Test
    public void tsHello(){
        //hessian服务的url 其中hessian-v1是项目名
        String url = "http://127.0.0.1:8080/lbWeb/hessianService";
        //创建HessianProxyFactory实例
        HessianProxyFactory factory = new HessianProxyFactory();
        //获得Hessian服务的远程引用
        try {
            HessianTsService hello = (HessianTsService)factory.create(HessianTsService.class,url);
//            System.out.println(hello.hello("李兵"));
            PersonVo monitor = hello.findMonitor(2);
            System.out.println(monitor.toJSON());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
