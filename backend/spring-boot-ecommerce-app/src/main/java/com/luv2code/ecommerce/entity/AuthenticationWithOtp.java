package com.luv2code.ecommerce.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "authentication_with_otp")
public class AuthenticationWithOtp {

	@Id
	@Column(name = "phone_no")
	private String PhoneNo;

	@Column(name = "otp")
	private String otp;

	@Column(name = "time")
	private LocalDateTime time;

}
