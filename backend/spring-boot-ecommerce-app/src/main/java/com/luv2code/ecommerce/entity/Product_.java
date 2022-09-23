package com.luv2code.ecommerce.entity;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Integer> unitPrice;
	public static volatile SingularAttribute<Product, String> imgUrl;
	public static volatile SingularAttribute<Product, Integer> unitsInStock;
	public static volatile SingularAttribute<Product, Date> lastUpdated;
	public static volatile SingularAttribute<Product, Date> dateCreated;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Boolean> active;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, ProductCategory> category;
	public static volatile SingularAttribute<Product, String> sku;

	public static final String UNIT_PRICE = "unitPrice";
	public static final String IMG_URL = "imgUrl";
	public static final String UNITS_IN_STOCK = "unitsInStock";
	public static final String LAST_UPDATED = "lastUpdated";
	public static final String DATE_CREATED = "dateCreated";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ACTIVE = "active";
	public static final String ID = "id";
	public static final String CATEGORY_ID = "categoryId";
	public static final String SKU = "sku";

}
