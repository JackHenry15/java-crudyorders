package com.lambdaschool.javaorders.services;

//import com.lambdaschool.javaorders.models.Agent;
import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl
        implements CustomerServices{
    @Autowired
    private CustomerRepository custrepos;

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findCustomerById(long id) throws EntityNotFoundException {
        return custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Override
    public List<Customer> findCustomerByName(String custname) {
        return custrepos.findByCustnameContainingIgnoringCase(custname);
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {

        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0){
            findCustomerById(customer.getCustcode());

            newCustomer.setCustcode(customer.getCustcode());
        }
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());
//        newCustomer.getAgent().clear();
//        for (Agent a : customer.getAgent())
        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders())
        {
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setCustomer(newCustomer);
            newOrder.setOrderdescription(o.getOrderdescription());
            newCustomer.getOrders().add(newOrder);
        }

        return custrepos.save(newCustomer);
    }
    @Transactional
    @Override
    public void delete(long id) {
        if (custrepos.findById(id).isPresent())
        {
            custrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer " + id + " Not Found");
        }
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id) {

        Customer currentCustomer = findCustomerById(id);

        if (customer.getCustname() != null)
        {
            currentCustomer.setCustname(customer.getCustname());
        }
        if (customer.getCustcity() != null) {
            currentCustomer.setCustcity(customer.getCustcity());
        }
        if (customer.getWorkingarea() != null) {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if (customer.getCustcountry() != null) {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }
        if (customer.getGrade() != null) {
            currentCustomer.setGrade(customer.getGrade());
        }
        if (customer.hasvalueforopeningamt){
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if (customer.hasvalueforreceiveamt){
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if (customer.hasvalueforpaymentamt){
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.hasvalueforoutstandingamt){
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if (customer.getPhone() != null) {
            currentCustomer.setPhone(customer.getPhone());
        }
//        if (customer.get)
//
//
//        currentCustomer.setAgent(customer.getAgent());
//        newCustomer.getAgent().clear();
//        for (Agent a : customer.getAgent())
        if (customer.getOrders().size() > 0) {
            currentCustomer.getOrders()
                    .clear();
            for (Order o : customer.getOrders()) {
                Order newOrder = new Order();
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setCustomer(currentCustomer);
                newOrder.setOrderdescription(o.getOrderdescription());
                currentCustomer.getOrders().add(newOrder);
            }
        }

        return custrepos.save(currentCustomer);
    }
}
