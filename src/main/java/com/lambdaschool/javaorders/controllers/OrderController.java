package com.lambdaschool.javaorders.controllers;


import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServices orderServices;

    //http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{ordernum}", produces = "application/json")
    public ResponseEntity<?> getOrderByNumber(@PathVariable long ordernum)
    {
        Order o = orderServices.findOrdersById(ordernum);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }


    //delete http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordernum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordernum)
    {
        orderServices.delete(ordernum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //POST http://localhost:2019/customers/customer

    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid
                                            @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ordernum}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(newOrder, responseHeaders,  HttpStatus.CREATED);
    }

    @PutMapping(value ="/order/{ordernum}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCompleteOrder(@Valid @RequestBody Order updateOrder,
                                                    @PathVariable long ordernum)
    {
        updateOrder.setOrdnum(ordernum);
        updateOrder = orderServices.save(updateOrder);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

}
