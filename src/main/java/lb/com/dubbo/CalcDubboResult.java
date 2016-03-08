package lb.com.dubbo;

import java.io.Serializable;

/**
 * Created by libing on 2016/3/8.
 */
public class CalcDubboResult implements Serializable{

    int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
