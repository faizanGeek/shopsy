package com.luv2code.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luv2code.ecommerce.entity.CoupanCode;
import com.luv2code.ecommerce.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	Customer findByPhoneNo(String phoneNo);

	@Query("SELECT c.coupanCode from Customer c where c.phoneNo=?1")
	CoupanCode findCoupanCodeByCustomerId(String phoneNo);
}
