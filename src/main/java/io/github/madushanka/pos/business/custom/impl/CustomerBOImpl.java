package io.github.madushanka.pos.business.custom.impl;

import io.github.madushanka.pos.business.custom.CustomerBO;
import io.github.madushanka.pos.business.exception.AlreadyExistsInOrderException;
import io.github.madushanka.pos.dao.custom.CustomerDAO;
import io.github.madushanka.pos.dao.custom.OrderDAO;
import io.github.madushanka.pos.db.HibernateUtill;
import io.github.madushanka.pos.dto.CustomerDTO;
import io.github.madushanka.pos.entity.Customer;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private OrderDAO orderDAO;

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        try(Session session=HibernateUtill.getSessionFactory().openSession()){
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }

    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        try(Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            customerDAO.setSession(session);
            session.beginTransaction();
            if (orderDAO.existsByCustomerId(customerId)) {
                throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
            }
                customerDAO.delete(customerId);
                session.getTransaction().commit();
        }
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            List<Customer> alCustomers = customerDAO.findAll();
            session.getTransaction().commit();
            List<CustomerDTO> dtos = new ArrayList<>();
            for (Customer customer : alCustomers) {
                dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
            }
            return dtos;
        }
    }

    @Override
    public String getLastCustomerId() throws Exception {
        try (Session session=HibernateUtill.getSessionFactory().openSession()){
            customerDAO.setSession(session);
            session.beginTransaction();
            String lastCustomerId = customerDAO.getLastCustomerId();
            session.getTransaction().commit();
            return lastCustomerId;
        }

    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        try(Session session = HibernateUtill.getSessionFactory().openSession()){
            customerDAO.setSession(session);
            session.beginTransaction();
            Customer customer = customerDAO.find(customerId);
            session.getTransaction().commit();
            return new CustomerDTO(customer.getCustomerId(),
                    customer.getName(), customer.getAddress());
        }
    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        try(Session session = HibernateUtill.getSessionFactory().openSession()){
            customerDAO.setSession(session);
            session.getTransaction().begin();
            List<Customer> customers = customerDAO.findAll();

            session.getTransaction().commit();
            List<String> ids = new ArrayList<>();
            for (Customer customer : customers) {
                ids.add(customer.getCustomerId());
            }
            return ids;
        }


    }
}
