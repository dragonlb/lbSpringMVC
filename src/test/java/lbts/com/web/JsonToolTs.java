package lbts.com.web;

import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libing on 2015/8/19.
 */
public class JsonToolTs {

    @Test
    public void string2map(){
        JSONObject jsonObject = JSONObject.fromObject(map2string());
        System.out.println(jsonObject.get("success"));
        System.out.println(jsonObject.get("respCode"));
        System.out.println(jsonObject.get("amt"));
        System.out.println("---------------------------------------------");
        System.out.println(jsonObject.getBoolean("success"));
        System.out.println(jsonObject.getString("respCode"));
        System.out.println(jsonObject.getDouble("amt"));
    }

    @Test
    public void tsMap2string(){
        String retSt = map2string();
        System.out.println(retSt);
    }

    public String map2string(){
        Map<String, Object> tsMap = new HashMap<String, Object>();
        tsMap.put("success", true);
        tsMap.put("respCode", "成功");
        tsMap.put("amt", 100);
        String retSt = JSONObject.fromObject(tsMap).toString();
        return retSt;
    }
}
