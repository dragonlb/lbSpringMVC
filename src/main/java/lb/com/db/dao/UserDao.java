package lb.com.db.dao;

import lb.com.db.bo.User;
import org.springframework.stereotype.Repository;

/**
 * Created by libing on 2015/5/19.
 */
@Repository
public class UserDao extends MyBatisBaseDao<User> {

    public UserDao() {
        super("USER");
    }
}
