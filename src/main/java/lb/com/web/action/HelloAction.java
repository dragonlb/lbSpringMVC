package lb.com.web.action;

import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libing on 2015/5/10.
 */
@Controller
public class HelloAction {

    @RequestMapping("/base/hello")
    @ResponseBody
    public Map<String, Object> getCommentCount(Model model, HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String name = req.getParameter("name");
        System.out.println("Hello. "+name);
        retMap.put("success", true);
        retMap.put("message", "成功");
        if(name!=null) {
            retMap.put("name", name);
        }
        return retMap;
    }

    @RequestMapping("/base/hello2")
    @ResponseBody
    public String sayHello2(Model model, HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String name = req.getParameter("name");
        System.out.println("Hello. "+name);
        retMap.put("success", true);
        retMap.put("message", "success2");
        if(name!=null) {
            retMap.put("name", name);
        }
        Thread.sleep(3*60*1000);
        System.out.println("after. "+name);
        return JSONArray.fromObject(retMap).toString();
    }
}
