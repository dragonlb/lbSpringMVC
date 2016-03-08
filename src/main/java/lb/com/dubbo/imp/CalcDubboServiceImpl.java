package lb.com.dubbo.imp;

import lb.com.dubbo.CalcDubboResult;
import lb.com.dubbo.CalcDubboService;
import org.springframework.stereotype.Service;

/**
 * Created by libing on 2016/3/7.
 */
@Service("calcDubboService")
public class CalcDubboServiceImpl implements CalcDubboService {

    public CalcDubboResult calc(int p1, int p2) {
        CalcDubboResult ret = new CalcDubboResult();
        System.out.println("Calc service ["+p1+"+"+p2+"+100]");
        ret.setVal(p1 + p2+100);
        return ret;
    }
}
