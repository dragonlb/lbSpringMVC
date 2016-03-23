package lb.com.elasticsearch.demo;

import lb.com.elasticsearch.demo.vo.MedicineVo;
import net.sf.json.JSONArray;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by libing on 2016/3/22.
 */
public class JsonUtil {

    /**
     * 实现将实体对象转换成json对象
     * @param medicine    Medicine对象
     * @return
     */
    public static String obj2JsonData(MedicineVo medicine){
        String jsonData = null;
        try {
            //使用XContentBuilder创建json数据
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id",medicine.getId())
                    .field("name", medicine.getName())
                    .field("funciton",medicine.getFunction())
                    .endObject();
            jsonData = jsonBuild.string();
//            jsonData = JSONArray.fromObject(medicine).toString();
            System.out.println(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonData;
    }

}
