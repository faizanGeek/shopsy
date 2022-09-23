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
public class CoupanCodeDTO {

	private Long coupanCodeId;

	private String code = null;

	private Double discount = null;

	private String customerPhoneNo;

}
