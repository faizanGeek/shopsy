package com.luv2code.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.ecommerce.dto.Colors;
import com.luv2code.ecommerce.dto.ProductCategoryDTO;
import com.luv2code.ecommerce.dto.ProductDTO;
import com.luv2code.ecommerce.dto.ProductsResponse;
import com.luv2code.ecommerce.exception.EcommerceException;
import com.luv2code.ecommerce.service.ProductService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductService productService;

	@RequestMapping(method = RequestMethod.GET, path = "/products")
	private ProductsResponse getAllProducts(@RequestParam(name = "category_id", required = false) Long categoryId,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "min_price", required = false) String minPrice,
			@RequestParam(name = "max_price", required = false) String maxPrice,
			@RequestParam(name = "sort_by_price", required = false) String sortByPrice) throws EcommerceException {
		System.out.println(minPrice + " " + maxPrice);
		return productService.getProducts(categoryId, name, minPrice, maxPrice, sortByPrice);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/product")
	private ProductDTO getProduct(@RequestParam(name = "id", required = true) Long id) {
		return productService.getProduct(id);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/product-category")
	private List<ProductCategoryDTO> getAllProducts() throws EcommerceException {
		return productService.getAllProductCategory();

	}

	@RequestMapping(method = RequestMethod.GET, path = "/colors")
	private List<Colors> getColors() throws EcommerceException {
		return productService.getColors();

	}

	@RequestMapping(method = RequestMethod.GET, path = "/productCategory")
	private ProductCategoryDTO getProductCategory(@RequestParam(name = "categoryId", required = false) Long categoryId)
			throws EcommerceException {
		return productService.getProductCategory(categoryId);

	}

}
