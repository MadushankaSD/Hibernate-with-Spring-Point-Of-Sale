package io.github.madushanka.pos.dao.custom.impl;


import io.github.madushanka.pos.dao.CrudDAOImpl;
import io.github.madushanka.pos.dao.custom.OrderDetailDAO;
import io.github.madushanka.pos.entity.OrderDetail;
import io.github.madushanka.pos.entity.OrderDetailPK;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail,OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsByItemCode(String itemCode) throws Exception {
       return getSession().createNativeQuery("SELECT * FROM OrderDetail WHERE Item_id=? LIMIT 1").setParameter(1, itemCode).uniqueResult() !=null;
    }
}
