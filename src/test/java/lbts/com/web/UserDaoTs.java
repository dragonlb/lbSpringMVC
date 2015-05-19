package lbts.com.web;

import lb.com.db.bo.User;
import lb.com.db.dao.UserDao;
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
public class UserDaoTs {

    private static final Log LOG = LogFactory.getLog(UserDaoTs.class);

    @Autowired
    UserDao userDao;

    @Test
    public void logTest(){
        LOG.debug("Hello LOG of debug.....");
        System.out.println("Hello sysout...." + userDao);
        User oneUser = new User();
        oneUser.setCode("001");
        oneUser.setName("Lb");
        oneUser.setDesp("测试描述2");
        oneUser.setSex(1);
        oneUser.setAddress("上海松江");
        userDao.insert(oneUser);
        System.out.println(oneUser.getId()+"\t"+oneUser.getName());
    }
}
