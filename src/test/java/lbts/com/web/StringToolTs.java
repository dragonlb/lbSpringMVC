package lbts.com.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by libing on 2015/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-config.xml"})
public class StringToolTs {


    private static final Log LOG = LogFactory.getLog(StringToolTs.class);

    @Test
    public void logTest(){
        LOG.debug("Hello LOG of debug.....");
        System.out.println("Hello sysout....");
    }
}
