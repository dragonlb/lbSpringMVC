package lb.com.db.service.impl;

import lb.com.db.bo.OrdOrder;
import lb.com.db.bo.User;
import lb.com.db.dao.OrdOrderDao;
import lb.com.db.dao.UserDao;
import lb.com.db.service.facade.OrdOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by libing on 2015/5/19.
 */
@Service("ordOrderService")
public class OrdOrderServiceImpl implements OrdOrderService {

    private static final Log LOG = LogFactory.getLog( OrdOrderServiceImpl.class );

    @Autowired
    UserDao userDao;

    @Autowired
    OrdOrderDao ordOrderDao;

    public void saveOrder(){
        LOG.debug("Hello LOG of debug....." + userDao);
        User oneUser = new User();
        oneUser.setCode("001");
        oneUser.setName("Lb");
        oneUser.setDesp("测试描述2");
        oneUser.setSex(1);
        oneUser.setAddress("上海松江");
        userDao.insert(oneUser);
        LOG.debug(oneUser.getId() + "\t" + oneUser.getName());

        OrdOrder oneOrder = new OrdOrder();
        oneOrder.setCreatedBy(oneUser.getId());
        oneOrder.setCreatedTime(new Date());
        oneOrder.setGoodsId(12);
        oneOrder.setQuantity(2);
        oneOrder.setPrice(160D);
        oneOrder.setAmt(oneOrder.getQuantity() * oneOrder.getPrice());
        oneOrder.setUseTime(new Date(oneOrder.getCreatedTime().getTime() + 3600 * 24 * 5));
        ordOrderDao.insert(oneOrder);
        LOG.debug("One order is " + oneOrder.getId() + "\t" + oneOrder.getUseTime());
    }
}
