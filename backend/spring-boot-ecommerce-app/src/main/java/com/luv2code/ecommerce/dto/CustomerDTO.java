package com.luv2code.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

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
public class CustomerDTO {

	private String phoneNo;

	private String name;

	private String password;

	private String repeatPassword;

	private List<OrderDTO> orders = new ArrayList<>();

	private List<AddressDTO> address = new ArrayList<>();

	private CartDTO customerCart;

	private List<CardDTO> card = new ArrayList<>();

	private List<CoupanCodeDTO> coupanCode = new ArrayList<>();

	private String otp;

}
