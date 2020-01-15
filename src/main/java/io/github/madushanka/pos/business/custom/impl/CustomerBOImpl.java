package io.github.madushanka.pos.business.custom.impl;

import io.github.madushanka.pos.business.custom.CustomerBO;
import io.github.madushanka.pos.business.exception.AlreadyExistsInOrderException;
import io.github.madushanka.pos.dao.custom.CustomerDAO;
import io.github.madushanka.pos.dao.custom.OrderDAO;
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
            customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
            customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {

            if (orderDAO.existsByCustomerId(customerId)) {
                throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
            }
                customerDAO.delete(customerId);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
            List<Customer> alCustomers = customerDAO.findAll();

            List<CustomerDTO> dtos = new ArrayList<>();
            for (Customer customer : alCustomers) {
                dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
            }
            return dtos;

    }

    @Override
    public String getLastCustomerId() throws Exception {

            String lastCustomerId = customerDAO.getLastCustomerId();

            return lastCustomerId;

    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {

            Customer customer = customerDAO.find(customerId);

            return new CustomerDTO(customer.getCustomerId(),
                    customer.getName(), customer.getAddress());

    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {

            List<Customer> customers = customerDAO.findAll();

            List<String> ids = new ArrayList<>();
            for (Customer customer : customers) {
                ids.add(customer.getCustomerId());
            }
            return ids;
    }
}
