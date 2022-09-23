package com.luv2code.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.ecommerce.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {

	Payment findByTxnId(String txnId);
}
