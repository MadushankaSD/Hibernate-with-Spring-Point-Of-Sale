package io.github.madushanka.pos.dao;

import io.github.madushanka.pos.entity.SuperEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T,ID> {
    @Autowired
    protected Session session;
    private Class<T> entity;

    public CrudDAOImpl() {
       entity= (Class<T>)((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() throws Exception {
         return session.createQuery("FROM "+entity.getName()).list();
    }

    @Override
    public T find(ID id) throws Exception {
        return session.get(entity,id);
    }

    @Override
    public void save(T entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.saveOrUpdate(entity);
    }

    @Override
    public void delete(ID id) throws Exception {
       session.delete(session.load(entity,id));
    }

    @Override
    public void getSession(Session session) {
        this.session=session;
    }
}
