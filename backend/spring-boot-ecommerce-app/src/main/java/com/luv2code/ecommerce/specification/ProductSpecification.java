package com.luv2code.ecommerce.specification;

import org.springframework.data.jpa.domain.Specification;

import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.Product_;

public class ProductSpecification {

	public static Specification<Product> hasCategoryId(Long categoryId) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			// System.out.println("catId " + categoryId);
			if (categoryId == null || categoryId == 0l)
				return criteriaBuilder.conjunction();
			return criteriaBuilder.equal(root.get(Product_.CATEGORY_ID), categoryId);
		});
	}

	public static Specification<Product> hasMinPrice(String min) {
		Integer mn = min == null ? 0 : Integer.parseInt(min);
		// System.out.println("min " + mn);
		return ((root, criteriaQuery, criteriaBuilder) -> {
			return criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.UNIT_PRICE), mn);
		});
	}

	public static Specification<Product> hasMaxPrice(String max) {
		Integer mx = max == null || max.equals("undefined") ? Integer.MAX_VALUE : Integer.parseInt(max);
		// System.out.println("max " + mx);
		return ((root, criteriaQuery, criteriaBuilder) -> {
			return criteriaBuilder.lessThanOrEqualTo(root.get(Product_.UNIT_PRICE), mx);
		});
	}

	public static Specification<Product> hasName(String name) {
		return ((root, criteriaQuery, criteriaBuilder) -> {
			// System.out.println("name " + name);
			if (name == null || name.isEmpty() || name.isBlank())
				return criteriaBuilder.conjunction();
			return criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
		});
	}

}
