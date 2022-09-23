package com.luv2code.ecommerce.dto;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class ProductDTO {

	private Long id;

	private Long categoryId;

	private String sku;

	private String name;

	private String description;

	private Double productDiscount;

	private Integer unitPrice;

	private String imgUrl;

	private boolean active;

	private int unitsInStock;

	@CreationTimestamp
	private Date dateCreated;

	@UpdateTimestamp
	private Date lastUpdated;

	private ProductCategoryDTO productCategory;

}
