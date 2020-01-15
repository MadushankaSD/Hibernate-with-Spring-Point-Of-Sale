package io.github.madushanka.pos.dao.custom.impl;


import io.github.madushanka.pos.dao.custom.QueryDAO;
import io.github.madushanka.pos.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryDAOImpl implements QueryDAO {

    private Session session;

    @Override
    public List<CustomEntity> getOrderInfo() throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT o.id as orderId, c.customerId as customerId, c.name as customerName, o.date as orderDate, SUM(od.qty * od.unitPrice) AS orderTotal  FROM Customer c INNER JOIN `order` o ON c.customerId=o.customerID INNER JOIN OrderDetail od on o.id = od.Order_id GROUP BY o.id");

        Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List<CustomEntity> list = query.list();
        System.out.println(list.toString());
        return list;
}
    @Override
    public void setSession(Session session) {
        this.session=session;
    }
}
