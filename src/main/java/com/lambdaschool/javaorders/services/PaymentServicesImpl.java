package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Transactional
@Service(value = "paymentServices")
public class PaymentServicesImpl implements PaymentServices{
    @Autowired
    private PaymentRepository paymentrepos;

    @Transactional
    @Override
    public Payment save(Payment payment) { return paymentrepos.save(payment); }
}
