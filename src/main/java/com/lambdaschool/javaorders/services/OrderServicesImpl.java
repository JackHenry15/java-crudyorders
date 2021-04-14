package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.OrderRepository;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices{
    @Autowired
    private OrderRepository ordersrepos;
    @Autowired
    private PaymentRepository paymentrepos;

    @Override
    public Order findOrdersById(long id) throws EntityNotFoundException {
        return ordersrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
    }


    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();
        if (order.getOrdnum() != 0){
            findOrdersById(order.getOrdnum());

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()){
            Payment newPay = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
            newOrder.getPayments().add(newPay);
        }

        return ordersrepos.save(newOrder);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (ordersrepos.findById(id).isPresent())
        {
            ordersrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }
}
