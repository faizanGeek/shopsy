package com.luv2code.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.ecommerce.entity.AuthenticationWithOtp;

public interface AuthenticationWithOtpRepository extends JpaRepository<AuthenticationWithOtp, String> {

}
