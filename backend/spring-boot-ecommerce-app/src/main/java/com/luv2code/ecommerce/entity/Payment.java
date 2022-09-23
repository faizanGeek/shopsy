package com.luv2code.ecommerce.entity;

import java.util.Date;

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
@Table(name = "Payment")
public class Payment {

	@Id
	@Column(name = "txn_id")
	private String txnId;

	@Column(name = "email")
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "product_info")
	private String productInfo;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "pay_id")
	private String payId;

	@Column(name = "mode")
	private String mode;

}
