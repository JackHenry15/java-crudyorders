package com.lambdaschool.javaorders.controllers;


import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerServices customerServices;

    //http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> rtnList = customerServices.findAllCustomers();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
    //http://localhost:2019/customers/customer/7
    //http://localhost:2019/customers/customer/77
    @GetMapping(value = "/customer/{custid}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custid)
    {
        Customer c = customerServices.findCustomerById(custid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
//
//    //http://localhost:2019/customers/namelike/mes
//    //http://localhost:2019/customers/namelike/cin
    @GetMapping(value = "/namelike/{custname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByName(@PathVariable String custname)
    {
        List<Customer> rtnList = customerServices.findCustomerByName(custname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/{custid}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long custid)
    {
        customerServices.delete(custid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //POST http://localhost:2019/customers/customer

    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid
                                            @RequestBody Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custid}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(newCustomer, responseHeaders,  HttpStatus.CREATED);
    }

    @PutMapping(value ="/customer/{custid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCompleteCustomer(@Valid @RequestBody Customer updateCustomer,
                                                    @PathVariable long custid)
    {
        updateCustomer.setCustcode(custid);
        updateCustomer = customerServices.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @PatchMapping(value ="/customer/{custid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer,
                                            @PathVariable long custid)
    {
        updateCustomer = customerServices.update(updateCustomer, custid);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }
}
