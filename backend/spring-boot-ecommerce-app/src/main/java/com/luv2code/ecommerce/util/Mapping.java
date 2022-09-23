package com.luv2code.ecommerce.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luv2code.ecommerce.dto.AddressDTO;
import com.luv2code.ecommerce.dto.CardDTO;
import com.luv2code.ecommerce.dto.CartDTO;
import com.luv2code.ecommerce.dto.CartItemDTO;
import com.luv2code.ecommerce.dto.CoupanCodeDTO;
import com.luv2code.ecommerce.dto.CustomerDTO;
import com.luv2code.ecommerce.dto.OrderDTO;
import com.luv2code.ecommerce.dto.OrderItemDTO;
import com.luv2code.ecommerce.dto.ProductCategoryDTO;
import com.luv2code.ecommerce.dto.ProductDTO;
import com.luv2code.ecommerce.entity.Address;
import com.luv2code.ecommerce.entity.Card;
import com.luv2code.ecommerce.entity.Cart;
import com.luv2code.ecommerce.entity.CartItem;
import com.luv2code.ecommerce.entity.CoupanCode;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.OrderItem;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.repository.ProductCategoryRepository;

@Component
public class Mapping {
	// productEntityAndProductDTO

	@Autowired
	ProductCategoryRepository productCategoryRepository;

	public ProductDTO getProductDTO(Product product) {

		ProductCategory productCategory = productCategoryRepository.findById(product.getCategoryId()).get();

		ProductDTO productDto = new ProductDTO();
		productDto.setActive(product.isActive());
		productDto.setDateCreated(product.getDateCreated());
		productDto.setDescription(product.getDescription());
		productDto.setId(product.getId());
		productDto.setImgUrl(product.getImgUrl());
		productDto.setLastUpdated(product.getLastUpdated());
		productDto.setName(product.getName());
		productDto.setSku(product.getSku());
		productDto.setUnitPrice(product.getUnitPrice());
		productDto.setProductDiscount(product.getProductDiscount());
		productDto.setUnitsInStock(product.getUnitsInStock());
		productDto.setCategoryId(product.getCategoryId());
		productDto.setProductCategory(getProductCategoryDTO(productCategory));

		return productDto;

	}

	public Product getProductEntity(ProductDTO product) {

		Product productEntity = new Product();
		productEntity.setActive(product.isActive());
		productEntity.setDateCreated(product.getDateCreated());
		productEntity.setDescription(product.getDescription());
		productEntity.setId(product.getId());
		productEntity.setImgUrl(product.getImgUrl());
		productEntity.setLastUpdated(product.getLastUpdated());
		productEntity.setName(product.getName());
		productEntity.setSku(product.getSku());
		productEntity.setUnitPrice(product.getUnitPrice());
		productEntity.setProductDiscount(product.getProductDiscount());
		productEntity.setUnitsInStock(product.getUnitsInStock());
		productEntity.setCategoryId(product.getCategoryId());

		return productEntity;

	}

	public ProductCategoryDTO getProductCategoryDTO(ProductCategory productCategory) {

		List<ProductDTO> productList = new ArrayList<>();
//		productCategory.getProducts().forEach(product -> {
//			productList.add(getProductDTO(product));
//		});
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(productCategory.getId(),
				productCategory.getCategoryName(), productList);
		return productCategoryDTO;
	}

	public ProductCategory getProductCategoryEntity(ProductCategoryDTO productCategory) {

		List<Product> productList = new ArrayList<>();
//		productCategory.getProducts().forEach(product -> {
//			productList.add(getProductEntity(product));
//		});
		ProductCategory productCategoryEntity = new ProductCategory(productCategory.getId(),
				productCategory.getCategoryName(), productList);
		return productCategoryEntity;
	}

	public CartItemDTO getCartItemDTO(CartItem cartItem) {
		CartItemDTO cartItemDTO = new CartItemDTO(cartItem.getId(), getProductDTO(cartItem.getProduct()),
				cartItem.getCartId(), cartItem.getQuantity());
		return cartItemDTO;
	}

	public CartItem getCartItemEntity(CartItemDTO cartItem) {
		CartItem cartItemEntity = new CartItem(cartItem.getId(), getProductEntity(cartItem.getProduct()),
				cartItem.getCartId(), cartItem.getQuantity());
		return cartItemEntity;
	}

	public CartDTO getCartDTO(Cart cart) {
		List<CartItemDTO> cartItemList = new ArrayList<>();
		cart.getCartItem().forEach(cartItem -> {
			cartItemList.add(getCartItemDTO(cartItem));
		});

		CartDTO cartDTO = new CartDTO(cart.getId(), cartItemList, cart.getTotalQuantity(), cart.getCartPrice(),
				cart.getTotalDiscountPer(), cart.getTotalPrice(), cart.getCoupanDiscountedPrice());

		return cartDTO;
	}

	public Cart getCartEntity(CartDTO cart) {
		Cart cartEntity = new Cart();
		if (cart != null) {
			List<CartItem> cartItemList = new ArrayList<>();
			cart.getCartItem().forEach(cartItem -> {
				cartItemList.add(getCartItemEntity(cartItem));
			});

			cartEntity = new Cart(cart.getId(), cartItemList, cart.getTotalQuantity(), cart.getCartPrice(),
					cart.getTotalDiscountPer(), cart.getTotalPrice(), cart.getCoupanDiscountedPrice());
		}

		return cartEntity;
	}

	public AddressDTO getAddressDTO(Address address) {

		AddressDTO addressDTO = new AddressDTO(address.getId(), address.getName(), address.getAddress(),
				address.getCity(), address.getState(), address.getPinCode(), address.getCustomerPhoneNo(),
				address.getContactNumber());
		return addressDTO;
	}

	public Address getAddressEntity(AddressDTO address) {

		Address addressEntity = new Address(address.getId(), address.getName(), address.getAddress(), address.getCity(),
				address.getState(), address.getPinCode(), address.getCustomerPhoneNo(), address.getContactNumber());
		return addressEntity;
	}

	public CardDTO getCardDTO(Card card) {

		CardDTO cardDTO = new CardDTO(card.getCardId(), card.getCardType(), card.getCardNumber(), card.getCvv(),
				card.getExpiryDate(), card.getNameOnCard(), card.getCustomerPhoneNo());
		return cardDTO;
	}

	public Card getCardEntity(CardDTO card) {

		Card cardEntity = new Card(card.getCardId(), card.getCardType(), card.getCardNumber(), card.getCvv(),
				card.getExpiryDate(), card.getNameOnCard(), card.getCustomerPhoneNo());
		return cardEntity;
	}

	public OrderItemDTO getOrderitemDTO(OrderItem orderItem) {

		OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem.getId(), getProductDTO(orderItem.getProduct()),
				orderItem.getOrderId());
		return orderItemDTO;
	}

	public OrderItem getOrderitemEntity(OrderItemDTO orderItem) {

		OrderItem orderItemEntity = new OrderItem(orderItem.getId(), getProductEntity(orderItem.getProduct()),
				orderItem.getOrderId());
		return orderItemEntity;
	}

	public OrderDTO getOrderDTO(Order order) {

//		List<OrderItemDTO> orderItemList = new ArrayList<>();
//		order.getOrderItems().forEach(orderItem -> {
//			orderItemList.add(getOrderitemDTO(orderItem));
//		});

//		OrderDTO orderDTO = new OrderDTO(order.getId(), order.getOrderNumber(), order.getOrderedCartId(),
//				order.getDateCreated(), order.getTotalPrice(), getAddressDTO(order.getShippingAddress()),
//				order.getStatus(), order.getPaymentThrough(), order.getDateOfDelivery(), order.getTimeofDelivery(),
//				order.getCustomerPhoneNo(), order.getTxnId());
//		return orderDTO;

	}

	public Order getOrderEntity(OrderDTO order) {

//		List<OrderItem> orderItemList = new ArrayList<>();
//		order.getOrderItems().forEach(orderItem -> {
//			orderItemList.add(getOrderitemEntity(orderItem));
//		});

		Order orderEntity = new Order(order.getId(), order.getOrderNumber(), order.getOrderedCartId(),
				order.getDateCreated(), order.getTotalPrice(), getAddressEntity(order.getShippingAddress()),
				order.getStatus(), order.getPaymentThrough(), order.getDateOfDelivery(), order.getTimeofDelivery(),
				order.getCustomerPhoneNo(), order.getTxnId());
		return orderEntity;

	}

	public CoupanCodeDTO getCoupanCodeDTO(CoupanCode coupanCode) {
		CoupanCodeDTO coupanCodeDTO = new CoupanCodeDTO(coupanCode.getCoupanCodeId(), coupanCode.getCode(),
				coupanCode.getDiscount(), coupanCode.getCustomerPhoneNo());
		return coupanCodeDTO;
	}

	public CoupanCode getCoupanCodeEntity(CoupanCodeDTO coupanCode) {
		CoupanCode coupanCodeEntity = new CoupanCode(coupanCode.getCoupanCodeId(), coupanCode.getCode(),
				coupanCode.getDiscount(), coupanCode.getCustomerPhoneNo());
		return coupanCodeEntity;
	}

	public CustomerDTO getCustomerDTO(Customer customer) {
		// TODO Auto-generated method stub
		List<OrderDTO> orderDTOList = new ArrayList<>();
		List<AddressDTO> addressDTOList = new ArrayList<>();
		List<CardDTO> cardDTOList = new ArrayList<>();
		List<CoupanCodeDTO> coupanCodeList = new ArrayList<>();

		customer.getOrders().forEach(order -> {
			orderDTOList.add(getOrderDTO(order));
		});

		customer.getAddress().forEach(address -> {
			addressDTOList.add(getAddressDTO(address));
		});

		customer.getCard().forEach(card -> {
			cardDTOList.add(getCardDTO(card));
		});
		customer.getCoupanCode().forEach(coupanCode -> {
			coupanCodeList.add(getCoupanCodeDTO(coupanCode));
		});
		customer.getCard().forEach(card -> {
			cardDTOList.add(getCardDTO(card));
		});
		CustomerDTO customerDTO = new CustomerDTO(customer.getPhoneNo(), customer.getName(), customer.getPassword(),
				customer.getPassword(), orderDTOList, addressDTOList, getCartDTO(customer.getCustomerCart()),
				cardDTOList, coupanCodeList, null);
		return customerDTO;
	}

	public Customer getCustomerEntity(CustomerDTO customer) {
		// TODO Auto-generated method stub
		List<Order> orderList = new ArrayList<>();
		List<Address> addressList = new ArrayList<>();
		List<Card> cardList = new ArrayList<>();
		List<CoupanCode> coupanCodeList = new ArrayList<>();

		customer.getOrders().forEach(order -> {
			orderList.add(getOrderEntity(order));
		});

		customer.getAddress().forEach(address -> {
			addressList.add(getAddressEntity(address));
		});

		customer.getCard().forEach(card -> {
			cardList.add(getCardEntity(card));
		});

		customer.getCoupanCode().forEach(coupanCode -> {
			coupanCodeList.add(getCoupanCodeEntity(coupanCode));
		});

		Customer customerEntity = new Customer(customer.getPhoneNo(), customer.getName(), customer.getPassword(),
				orderList, addressList, getCartEntity(customer.getCustomerCart()), cardList, coupanCodeList);
		return customerEntity;
	}

}
