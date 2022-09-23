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
public class PaymentDTO {

	private String txnId;

	private String email;

	private String name;

	private String phoneNo;

	private String contactNo;

	private String productInfo;

	private String amount;

	private String hash;

	private String sUrl;

	private String fUrl;

	private String key;

}
