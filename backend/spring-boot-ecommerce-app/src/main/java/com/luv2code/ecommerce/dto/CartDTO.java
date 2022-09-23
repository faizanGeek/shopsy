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
public class CartDTO {

	private Long id;

	private List<CartItemDTO> cartItem = new ArrayList<>();

	private Integer totalQuantity;

	private Double cartPrice;

	private Double totalDiscountPer;

	private Double totalPrice;

	private Double coupanDiscountedPrice = 0.0;

}
