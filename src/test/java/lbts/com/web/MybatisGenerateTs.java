package lbts.com.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by libing on 2015/5/12.
 */
public class MybatisGenerateTs {

    public static void generator_oracle(){
        File configFile = new File("E:\\Lb\\GitHub\\lbSpringMVC\\src\\main\\resources\\bankDB.xml");
        generator(configFile);
    }

    public static void generator_mysql(){
        File configFile = new File("E:\\Lb\\GitHub\\lbSpringMVC\\src\\main\\resources\\bankMySql.xml");
        generator(configFile);
    }

    @Test
    public void ddtt(){
        System.out.println("xxxxxxxxxxxxxxxx");
    }

    private static void generator(File genXml){
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        try {
            File configFile = genXml;
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args){
//        generator_oracle();
        generator_mysql();
    }
}
