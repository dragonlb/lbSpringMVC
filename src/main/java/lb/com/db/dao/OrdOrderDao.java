package lb.com.db.dao;

import lb.com.db.bo.OrdOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by libing on 2015/5/19.
 */
@Repository
public class OrdOrderDao extends MyBatisBaseDao<OrdOrder> {

    public OrdOrderDao() {
        super("ORD_ORDER");
    }
}
