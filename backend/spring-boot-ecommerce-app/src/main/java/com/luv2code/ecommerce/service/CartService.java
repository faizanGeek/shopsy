package com.luv2code.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.dto.CartDTO;
import com.luv2code.ecommerce.dto.CartItemDTO;
import com.luv2code.ecommerce.dto.CartItemUpdate;
import com.luv2code.ecommerce.dto.CoupanCodeDTO;
import com.luv2code.ecommerce.dto.CustomerDTO;
import com.luv2code.ecommerce.entity.Cart;
import com.luv2code.ecommerce.entity.CartItem;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.exception.EcommerceException;
import com.luv2code.ecommerce.repository.CartItemRepository;
import com.luv2code.ecommerce.repository.CartRepository;
//import com.luv2code.ecommerce.repository.cartItemRepository;
import com.luv2code.ecommerce.repository.CustomerRepository;
import com.luv2code.ecommerce.util.Mapping;

@Service
public class CartService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private Mapping mapping;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartRepository cartRepository;

	public List<CartItemDTO> getCartItemById(long cartId) {
		List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
		List<CartItemDTO> cartItemsDTO = new ArrayList<>();
		cartItems.forEach(items -> {
			cartItemsDTO.add(mapping.getCartItemDTO(items));
		});

		return cartItemsDTO;
	}

	public CartDTO getCart(String emailId) {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findById(emailId).get();
		CartDTO cartDTO = new CartDTO();
		if (customer != null) {
			cartDTO = mapping.getCartDTO(customer.getCustomerCart());
		}
		return cartDTO;
	}

	public String addCartItem(CartItemDTO cartItemDTO, String phoneId) {
		CartItem cartItem = new CartItem();
		cartItem = mapping.getCartItemEntity(cartItemDTO);
		cartItemRepository.save(cartItem);
		updateCartIncrement(cartItemDTO);
		return "Cart Item Added";

	}

	// @Modifying
	public CartItemDTO updateCartItemQuantity(CartItemUpdate cartItemupdate) {
		CartItem cartItem = new CartItem();
		cartItem = cartItemRepository.findById(cartItemupdate.getCartItemId()).get();
		if (cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity() + cartItemupdate.getQuantity());
			cartItemRepository.save(cartItem);
		}

		// TODO Auto-generated method stub
		if (cartItemupdate.getQuantity() == 1) {
			updateCartIncrement(mapping.getCartItemDTO(cartItem));
		} else {
			updateCartDecrement(mapping.getCartItemDTO(cartItem));
		}
		return mapping.getCartItemDTO(cartItem);
	}

	public String deleteCartItem(long cartItemId) {
		CartItem cartItem = new CartItem();
		cartItem = cartItemRepository.findById(cartItemId).get();
		if (cartItem != null) {
			cartItemRepository.delete(cartItem);
		}
		updateCartOnDeleteItem(mapping.getCartItemDTO(cartItem));
		// TODO Auto-generated method stub
		return "Deleted";
	}

	public CoupanCodeDTO getCoupanCode(String code, String token) throws EcommerceException {
		// TODO Auto-generated method stub
		CustomerDTO customerDTO = customerService.getCustomerDetail(token);
		CoupanCodeDTO coupanCodeDTO = null;
		System.out.println(code);
		if (code.equals("null")) {
			System.out.println("coupan");

			removeCoupanDiscount(customerDTO);
		} else {
			if (customerDTO != null && customerDTO.getCoupanCode() != null) {
				for (CoupanCodeDTO coupanCode : customerDTO.getCoupanCode()) {
					if (coupanCode.getCode().equalsIgnoreCase(code)) {
						coupanCodeDTO = coupanCode;
						updateCoupanDiscount(customerDTO, coupanCodeDTO);
					}
				}
			}
			if (coupanCodeDTO == null) {
				throw new EcommerceException("CoupanCode.NO_FOUND");
			}
		}

		return coupanCodeDTO;
	}

	private void removeCoupanDiscount(CustomerDTO customerDTO) {
		Customer customer = mapping.getCustomerEntity(customerDTO);
		customer.getCustomerCart().setCoupanDiscountedPrice(0.0);
		customerRepository.save(customer);
		// TODO Auto-generated method stub

	}

	private void updateCoupanDiscount(CustomerDTO customerDTO, CoupanCodeDTO coupanCodeDTO) {
		Customer customer = mapping.getCustomerEntity(customerDTO);
		Double coupanDiscountedPrice = (customer.getCustomerCart().getTotalPrice() * coupanCodeDTO.getDiscount()) / 100;
		customer.getCustomerCart().setCoupanDiscountedPrice(coupanDiscountedPrice);
		customerRepository.save(customer);
		// TODO Auto-generated method stub

	}

	public void updateCartIncrement(CartItemDTO cartItemDTO) {
		// TODO Auto-generated method stub
		// Customer customer = customerRepository.findByPhoneNo(phoneNo);
		System.out.println("Im increment");
		Cart cart = cartRepository.findById(cartItemDTO.getCartId()).get();
		Double avgDiscount = (cart.getTotalDiscountPer() * cart.getTotalQuantity()
				+ cartItemDTO.getProduct().getProductDiscount()) / (cart.getTotalQuantity() + 1);
		Double cartPrice = cart.getCartPrice() + cartItemDTO.getProduct().getUnitPrice();
		cart.setTotalDiscountPer(avgDiscount);
		cart.setCartPrice(cartPrice);
		cart.setTotalQuantity(cart.getTotalQuantity() + 1);
		cart.setTotalPrice(cartPrice - (cartPrice * avgDiscount) / 100);
		cartRepository.save(cart);
	}

	private void updateCartDecrement(CartItemDTO cartItemDTO) {
		System.out.println("Im decrement");

		// TODO Auto-generated method stub
		Cart cart = cartRepository.findById(cartItemDTO.getCartId()).get();
		Double avgDiscount = (cart.getTotalDiscountPer() * cart.getTotalQuantity()
				- cartItemDTO.getProduct().getProductDiscount()) / (cart.getTotalQuantity() - 1);
		Double cartPrice = cart.getCartPrice() - cartItemDTO.getProduct().getUnitPrice();
		cart.setTotalDiscountPer(avgDiscount);
		cart.setCartPrice(cartPrice);
		cart.setTotalQuantity(cart.getTotalQuantity() - 1);
		cart.setTotalPrice(cartPrice - (cartPrice * avgDiscount) / 100);
		cartRepository.save(cart);
	}

	private void updateCartOnDeleteItem(CartItemDTO cartItemDTO) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findById(cartItemDTO.getCartId()).get();
		Double avgDiscount = (cart.getTotalDiscountPer() * cart.getTotalQuantity()
				- cartItemDTO.getProduct().getProductDiscount() * cartItemDTO.getQuantity())
				/ (cart.getTotalQuantity() - cartItemDTO.getQuantity());
		Double cartPrice = cart.getCartPrice() - cartItemDTO.getProduct().getUnitPrice() * cartItemDTO.getQuantity();
		cart.setTotalDiscountPer(avgDiscount);
		cart.setCartPrice(cartPrice);
		cart.setTotalQuantity(cart.getTotalQuantity() - cartItemDTO.getQuantity());
		cart.setTotalPrice(cartPrice - (cartPrice * avgDiscount) / 100);
		cartRepository.save(cart);
	}

}
