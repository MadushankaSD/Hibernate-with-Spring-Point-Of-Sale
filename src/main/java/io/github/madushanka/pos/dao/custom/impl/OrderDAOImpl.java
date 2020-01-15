package io.github.madushanka.pos.dao.custom.impl;



import io.github.madushanka.pos.dao.CrudDAOImpl;
import io.github.madushanka.pos.dao.custom.OrderDAO;
import io.github.madushanka.pos.entity.Order;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends CrudDAOImpl<Order,Integer> implements OrderDAO {

    @Override
    public int getLastOrderId() throws Exception {
        Object object = getSession().createNativeQuery("SELECT id FROM `order` ORDER BY id DESC LIMIT 1").uniqueResult();
        return object==null?0: (int) object;
    }

    @Override
    public boolean existsByCustomerId(String customerId) throws Exception {
        NativeQuery nativeQuery = getSession().createNativeQuery("SELECT * FROM `order` WHERE customerId=?");
        nativeQuery.setParameter(1, customerId);

        return nativeQuery.uniqueResult()!=null;

    }
}
