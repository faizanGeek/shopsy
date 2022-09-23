package com.luv2code.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.luv2code.ecommerce.entity.PaymentThrough;

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
public class OrderDTO {

	private Long id;

	private String orderNumber;

	private Long orderedCartId;

	private Date dateCreated;

	private BigDecimal totalPrice;

	private ShippingAddressDTO shippingAddress;

	private String status;

	private PaymentThrough paymentThrough;

	private LocalDateTime dateOfDelivery;

	private String TimeofDelivery;

	private String customerPhoneNo;

	private String txnId;
}
