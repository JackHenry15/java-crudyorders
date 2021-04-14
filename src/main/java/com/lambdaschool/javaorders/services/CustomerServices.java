package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;

import java.util.List;

public interface CustomerServices {


    List<Customer> findAllCustomers();
    Customer findCustomerById(long id);
    List<Customer> findCustomerByName(String custname);
    Customer save(Customer customer);
    void delete(long id);
    Customer update(Customer customer, long id);

}
