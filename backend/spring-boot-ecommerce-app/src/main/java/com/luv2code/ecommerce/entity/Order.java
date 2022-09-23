package com.luv2code.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "order_number")
	private String orderNumber;

	@Column(name = "cart_id")
	private Long orderedCartId;

	@Column(name = "date_created")
	@CreationTimestamp
	private Date dateCreated;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id")
	private ShippingAddress shippingAddress;

	@Column(name = "status")
	private String status;

	@Column(name = "payment_through")
	@Enumerated(EnumType.STRING)
	private PaymentThrough paymentThrough;

	@Column(name = "date_of_delivery")
	private LocalDateTime dateOfDelivery;

	@Column(name = "time_of_delivery")
	private String TimeofDelivery;

	@Column(name = "customer_phone_no")
	private String customerPhoneNo;

	@Column(name = "txn_id")
	private String txnId;

}
