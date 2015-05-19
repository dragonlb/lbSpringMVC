package lb.com.db.service.impl;


import lb.com.db.dao.UserDao;
import lb.com.db.service.facade.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by libing on 2015/5/19.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;


    public void saveOrder(){

    }
}
