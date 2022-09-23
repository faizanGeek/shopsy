package com.luv2code.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
