package lbts.com.web;

import lb.com.db.bo.User;
import lb.com.db.dao.UserDao;
import lb.com.db.service.facade.OrdOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by libing on 2015/5/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-config.xml"})
public class OrdOrderServiceImplTs {

    private static final Log LOG = LogFactory.getLog(OrdOrderServiceImplTs.class);

    @Autowired
    OrdOrderService ordOrderService;

    @Test
    public void saveOrderTest(){
        LOG.debug("Ts ordOrderServiceImplTs start.");
        ordOrderService.saveOrder();
        LOG.debug("Ts ordOrderServiceImplTs end.");
    }
}
