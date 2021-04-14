package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;

public interface OrderServices {
    Order findOrdersById(long id);
    Order save(Order order);
    void delete(long id);
}
