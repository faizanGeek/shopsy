package com.luv2code.ecommerce.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "customer_cart")
public class Cart {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	private List<CartItem> cartItem = new ArrayList<>();

	@Column(name = "total_quantity")
	private Integer totalQuantity = 0;

	@Column(name = "cart_price")
	private Double cartPrice = 0.0;

	@Column(name = "total_discount_per")
	private Double totalDiscountPer = 0.0;

	@Column(name = "total_price")
	private Double totalPrice = 0.0;

	@Column(name = "coupan_discount_price")
	private Double coupanDiscountedPrice = 0.0;
}
