package com.luv2code.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.ecommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
