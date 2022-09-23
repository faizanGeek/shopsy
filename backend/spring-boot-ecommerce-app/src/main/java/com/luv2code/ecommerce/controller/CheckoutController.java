package com.luv2code.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.MediaType;
//import org.springframework.http.MediaType;
//import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.ecommerce.dto.OrderDTO;
import com.luv2code.ecommerce.dto.PaymentCallback;
import com.luv2code.ecommerce.dto.PaymentDTO;
import com.luv2code.ecommerce.service.CheckoutService;
import com.luv2code.ecommerce.service.PaymentService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class CheckoutController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private CheckoutService checkoutService;

	@RequestMapping(path = "/payment-details", method = RequestMethod.POST)
	private ResponseEntity<PaymentDTO> proceedPayment(@RequestBody PaymentDTO paymentDetail) {
		PaymentDTO paymentDetailDTO = paymentService.proceedPayment(paymentDetail);
		return new ResponseEntity<>(paymentDetailDTO, HttpStatus.OK);
	}

	@RequestMapping(path = "/payment-response", method = RequestMethod.POST)
	public ResponseEntity<String> payuCallback(@RequestBody PaymentCallback paymentCallback) {
		String msg = paymentService.payuCallback(paymentCallback);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@RequestMapping(path = "/order", method = RequestMethod.POST)
	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {
		// String msg = paymentService.payuCallback(paymentCallback);
		System.out.println(orderDTO);
		String msg = checkoutService.placeOrder(orderDTO);

		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}
