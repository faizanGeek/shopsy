package com.luv2code.ecommerce.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.dto.Colors;
import com.luv2code.ecommerce.dto.ProductCategoryDTO;
import com.luv2code.ecommerce.dto.ProductDTO;
import com.luv2code.ecommerce.dto.ProductsResponse;
import com.luv2code.ecommerce.dto.SortByPrice;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.exception.EcommerceException;
import com.luv2code.ecommerce.repository.ProductCategoryRepository;
import com.luv2code.ecommerce.repository.ProductRepository;
import com.luv2code.ecommerce.specification.ProductSpecification;
import com.luv2code.ecommerce.util.Mapping;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductCategoryRepository productCategoryRepository;

	@Autowired
	Mapping mapping;

	public ProductsResponse getProducts(Long categoryId, String name, String minPrice, String maxPrice,
			String sortByPrice) {

		Long size = 0l;
		System.out.println("catId " + categoryId);
		System.out.println("min " + minPrice);
		System.out.println("max " + maxPrice);
		System.out.println("name " + name);

		Specification<Product> specifications = Specification
				.where((ProductSpecification.hasCategoryId(categoryId)).and(ProductSpecification.hasMinPrice(minPrice)
						.and(ProductSpecification.hasMaxPrice(maxPrice).and(ProductSpecification.hasName(name)))));
		size = productRepository.count(specifications);
		List<Product> productEntityList = productRepository.findAll(specifications);

		List<ProductDTO> productList = new ArrayList<>();

		productEntityList.forEach(product -> {
			productList.add(mapping.getProductDTO(product));
		});

		ProductsResponse productResponse = new ProductsResponse();
		// String x = sortByPrice.toUpperCase();
		// .// (x.equals(x))
		if (sortByPrice == null || sortByPrice.toUpperCase().equals(SortByPrice.ANY.name())) {
			productResponse.setProductList(productList);
		} else if (sortByPrice != null && sortByPrice.toUpperCase().equals(SortByPrice.LOWEST.name())) {
			productResponse.setProductList(byAscOrder(productList));

		} else if (sortByPrice != null && sortByPrice.toUpperCase().equals(SortByPrice.HIGHEST.name())) {
			productResponse.setProductList(byDscOrder(productList));
		}
		productResponse.getPageDetail().setTotalElements(size);

		return productResponse;
	}

	private List<ProductDTO> byDscOrder(List<ProductDTO> productList) {
		// TODO Auto-generated method stub
		Collections.sort(productList, (p1, p2) -> p2.getUnitPrice().intValue() - p1.getUnitPrice().intValue());

		return productList;
	}

	private List<ProductDTO> byAscOrder(List<ProductDTO> productList) {
		// TODO Auto-generated method stub
		Collections.sort(productList, (p1, p2) -> p1.getUnitPrice().intValue() - p2.getUnitPrice().intValue());
		return productList;
	}

	public ProductDTO getProduct(Long id) {
		Optional<Product> product = Optional.empty();
		product = productRepository.findById(id);
		return mapping.getProductDTO(product.get());
	}

	public List<ProductCategoryDTO> getAllProductCategory() throws EcommerceException {

		List<ProductCategory> productCategoryEntityList = (List<ProductCategory>) productCategoryRepository.findAll();
		List<ProductCategoryDTO> productCategoryDTOs = new ArrayList<ProductCategoryDTO>();
		System.out.println("hi");
		for (ProductCategory productCategory : productCategoryEntityList) {
			ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
			productCategoryDTO.setId(productCategory.getId());
			productCategoryDTO.setCategoryName(productCategory.getCategoryName());
			productCategoryDTOs.add(productCategoryDTO);
		}
		return productCategoryDTOs;
	}

	public Pageable pagination(Integer pageNo) {
		if (pageNo == null)
			pageNo = 0;
		Pageable pageable = PageRequest.of(pageNo, 12);

		return pageable;

	}

//	public PageDetail populatePageDetail(Integer pageNo, Long size) {
//		if (pageNo == null)
//			pageNo = 0;
//		PageDetail pageDetail = new PageDetail();
//		pageDetail.setPageNo(pageNo.intValue());
//		pageDetail.setSize(12);
//		pageDetail.setTotalElements(size.intValue());
//		pageDetail.setTotalPages(size.intValue() / 12 + 1);
//		return pageDetail;
//	}

	public ProductsResponse getAllProducts() {
		// TODO Auto-generated method stub
		List<Product> productEntityList = productRepository.findAll();
		List<ProductDTO> productList = new ArrayList<>();

		productEntityList.forEach(product -> {
			productList.add(mapping.getProductDTO(product));
		});

		ProductsResponse productResponse = new ProductsResponse();
		productResponse.setProductList(productList);
		Long size = productRepository.count();
		// productResponse.setPageDetail(populatePageDetail(null, size));

		return productResponse;
		// return null;
	}

	public List<Colors> getColors() {
		// TODO Auto-generated method stub
		List<Colors> colors = new ArrayList<>();
		colors.add(new Colors("Black", "#000000"));
		colors.add(new Colors("White", "#F2F4F4"));
		colors.add(new Colors("Red", "#FF0000"));
		colors.add(new Colors("Green", "#00FF00"));
		colors.add(new Colors("Blue", "#0000FF"));
		colors.add(new Colors("Yellow", "#FFFF00"));
		colors.add(new Colors("Orange", "#FFA500"));
		colors.add(new Colors("Pink", "#FFC0CB"));
		colors.add(new Colors("Purple", "#800080"));

		return colors;
	}

	public ProductCategoryDTO getProductCategory(Long categoryId) {
		// TODO Auto-generated method stub
		ProductCategory productCategory = productCategoryRepository.findById(categoryId).get();
		return mapping.getProductCategoryDTO(productCategory);
	}

}
