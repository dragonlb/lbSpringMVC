package lbts.com.dubbo;

import lb.com.dubbo.CalcDubboResult;
import lb.com.dubbo.CalcDubboService;
import lb.com.dubbo.DubboService;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * Created by libing on 2016/3/8.
 */
public class DubboConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-dubbo-customer.xml"});
        context.start();

        DubboService demoService = (DubboService)context.getBean("dubboServiceRemote"); // 获取远程服务代理
        String hello = demoService.sayHello("world"); // 执行远程方法

        System.out.println(hello); // 显示调用结果

        CalcDubboService calcService = (CalcDubboService)context.getBean("calcDubboServiceRemote"); // 获取远程服务代理
        CalcDubboResult result = calcService.calc(23, 17); // 执行远程方法
        System.out.println("result.val = "+result.getVal()); // 显示调用结果
    }

}
