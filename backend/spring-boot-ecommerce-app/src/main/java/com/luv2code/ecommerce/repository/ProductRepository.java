package com.luv2code.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luv2code.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@Query("SELECT p from Product p where p.categoryId=?1")
	List<Product> findByProductCategory(Long categoryId, Pageable pageable);

	@Query("Select p from Product p where p.name LIKE %?1%")
	List<Product> findByContaining(String name, Pageable pageable);

	@Query("Select count(p) from Product p where p.name LIKE %?1%")
	Long countByProductContaining(String name);

	@Query("SELECT p from Product p where p.categoryId=?1 AND p.name LIKE %?2%")
	Page<Product> findbyFilters(Long categoryId, String name, Pageable pageable);

	@Query("SELECT count(p) from Product p where p.categoryId=?1 AND p.name LIKE %?2%")
	Long countbyFilters(Long categoryId, String name);

}
