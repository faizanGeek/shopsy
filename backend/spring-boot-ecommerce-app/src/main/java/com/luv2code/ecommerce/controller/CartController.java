package com.luv2code.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.ecommerce.dto.CartDTO;
import com.luv2code.ecommerce.dto.CartItemDTO;
import com.luv2code.ecommerce.dto.CartItemUpdate;
import com.luv2code.ecommerce.dto.CoupanCodeDTO;
import com.luv2code.ecommerce.exception.EcommerceException;
import com.luv2code.ecommerce.service.CartService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class CartController {

	Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private Environment environment;

	@Autowired
	private CartService cartService;

	@RequestMapping(method = RequestMethod.GET, path = "/getCartItems")
	private ResponseEntity<List<CartItemDTO>> getAllCartItem(
			@RequestParam(name = "cartId", required = true) long cartId) {
		
		List<CartItemDTO> cartItems = new ArrayList<>();
		cartItems = cartService.getCartItemById(cartId);
		return new ResponseEntity<>(cartItems, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/cart")
	private ResponseEntity<CartDTO> getCartItems(@RequestParam(name = "emailId", required = true) String emailId) {
		CartDTO cartDTO = cartService.getCart(emailId);
		return new ResponseEntity<>(cartDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/cartItem")
	private ResponseEntity<String> addCartItem(@RequestParam(name = "phoneId", required = true) String phoneId,
			@RequestBody CartItemDTO cartItemDTO) {
		// System.out.println(phoneId);
		// System.out.println(cartItemDTO);
		String response = cartService.addCartItem(cartItemDTO, phoneId);
		return new ResponseEntity<>(response, HttpStatus.OK);
		// return null;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/updateCartQuantity")
	private ResponseEntity<CartItemDTO> updateCartItemQuantity(
			@RequestParam(name = "phoneId", required = true) String phoneId,
			@RequestBody CartItemUpdate cartItemupdate) {

		CartItemDTO response = cartService.updateCartItemQuantity(cartItemupdate);
		return new ResponseEntity<>(response, HttpStatus.OK);
		// return null;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteCartItem")
	private ResponseEntity<String> deleteCartItem(@RequestParam(name = "cartItemId", required = true) long cartItemId) {

		String response = cartService.deleteCartItem(cartItemId);
		return new ResponseEntity<>(response, HttpStatus.OK);
		// return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/applyCode")
	private ResponseEntity<CoupanCodeDTO> applyCode(@RequestParam(name = "code", required = true) String code,
			@RequestParam(name = "token", required = true) String token) throws EcommerceException {
		// List<CartItemDTO> cartItems = new ArrayList<>();
		// cartItems = cartService.getCartItemById(cartId);
		CoupanCodeDTO coupanCodeDTO = cartService.getCoupanCode(code, token);
		return new ResponseEntity<>(coupanCodeDTO, HttpStatus.OK);
	}

//	@RequestMapping(method = RequestMethod.PUT, path = "/updateCart")
//	private ResponseEntity<String> updateCart(@RequestBody String phoneNo) {
//
//		String response = cartService.updateCart(phoneNo);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//		// return null;
//	}

}
