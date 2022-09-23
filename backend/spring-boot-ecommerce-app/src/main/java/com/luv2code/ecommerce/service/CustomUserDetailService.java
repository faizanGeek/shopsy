package com.luv2code.ecommerce.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.repository.CustomerRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private OtpService otpService;

	@Override
	public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {

		// TODO Auto-generated method stub // Customer customer =
		System.out.println(phoneNo + "hi");
		boolean isPresent = customerRepository.findById(phoneNo).isPresent();
		System.out.println(isPresent);
		Customer customer = customerRepository.findByPhoneNo(phoneNo);
		if (customer != null) {
			return new User(customer.getPhoneNo(), "dummy", new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("notfound");
		}
//		  String status = otpService.verifyOtp(phoneNo, otp);
//		  if (customer != null) { // return
//		   (UserDetails) customerDTO;
//		   return new User(customer.getEmailId(),customer.getPassword(), new ArrayList<>());
//		   } else {
//		  throw new UsernameNotFoundException("notfound");
//		  }
		// return null;
	}

}
