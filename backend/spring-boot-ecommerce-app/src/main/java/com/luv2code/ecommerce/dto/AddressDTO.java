package com.luv2code.ecommerce.dto;

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
public class AddressDTO {

	private Long id;

	private String name;

	private String address;

	private String city;

	private String state;

	private String pinCode;

	private String customerPhoneNo;

	private String contactNumber;
}
