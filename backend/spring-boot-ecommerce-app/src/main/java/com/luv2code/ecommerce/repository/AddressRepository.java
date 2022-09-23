package com.luv2code.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.ecommerce.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByCustomerPhoneNo(String emailId);
}
