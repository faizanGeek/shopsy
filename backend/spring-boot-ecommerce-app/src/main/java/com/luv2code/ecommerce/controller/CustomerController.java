package com.luv2code.ecommerce.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.ecommerce.config.JwtUtil;
import com.luv2code.ecommerce.dto.AddressDTO;
import com.luv2code.ecommerce.dto.CustomerDTO;
import com.luv2code.ecommerce.dto.JwtResponse;
import com.luv2code.ecommerce.dto.SmsDTO;
import com.luv2code.ecommerce.exception.EcommerceException;
import com.luv2code.ecommerce.service.CustomUserDetailService;
import com.luv2code.ecommerce.service.CustomerService;
import com.luv2code.ecommerce.service.OtpService;
import com.twilio.exception.ApiException;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private Environment environment;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private SimpMessagingTemplate webSocket;

	@Autowired
	private OtpService otpService;

	@Autowired
	private JwtUtil jwtUtil;

	private final String TOPIC_DESTINATION = "/topic/sendOtp";

	@RequestMapping(method = RequestMethod.POST, path = "/customer")
	private ResponseEntity<String> regsiterCustomer(@RequestBody CustomerDTO customerDTO) throws EcommerceException {
		System.out.println("hi");
		String registeredEmailId = customerService.registerCustomer(customerDTO);
		return new ResponseEntity<>(registeredEmailId, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/sendOtp", method = RequestMethod.POST)
	public ResponseEntity<String> sendOTP(@RequestBody SmsDTO smsDTO) throws ExecutionException, InterruptedException {

		String OTP;
		try {
			OTP = String.valueOf(otpService.sendOTP(smsDTO));
		} catch (ApiException e) {

			webSocket.convertAndSend(TOPIC_DESTINATION, ": Error sending the SMS: " + e.getMessage());
			throw e;
		}
		webSocket.convertAndSend(TOPIC_DESTINATION, ": SMS has been sent!: " + smsDTO.getTo());

		return new ResponseEntity<>(OTP, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/token")
	private ResponseEntity<JwtResponse> generateToken(@RequestBody CustomerDTO customerDTO)
			throws EcommerceException, ExecutionException {

		otpService.verifyOtp(customerDTO.getPhoneNo(), customerDTO.getOtp());
		customerService.registerCustomer(customerDTO);
		String token = this.jwtUtil.generateToken(customerDTO);
		JwtResponse jwtResponse = new JwtResponse(token, customerDTO.getPhoneNo());
		System.out.println(token);
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, path = "/customerDetail")
	private ResponseEntity<CustomerDTO> getCustomerDetails(
			@RequestParam(name = "token", required = true) String token) {

		CustomerDTO customerDTO = customerService.getCustomerDetail(token);
		return new ResponseEntity<>(customerDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/address")
	private ResponseEntity<String> addAddress(@RequestBody AddressDTO addressDTO) {
		// System.out.println();
		String response = customerService.addAddress(addressDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/address")
	private ResponseEntity<List<AddressDTO>> getAddresses(
			@RequestParam(name = "emailId", required = true) String emailId) {

		List<AddressDTO> addressDTOList = customerService.getAddresses(emailId);
		return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/updateAddress")
	private ResponseEntity<String> updateAddress(@RequestBody AddressDTO addressDTO) {

		String response = customerService.updateAddress(addressDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/deleteAddress")
	private ResponseEntity<String> deleteAddress(@RequestBody AddressDTO addressDTO) {

		String response = customerService.deleteAddress(addressDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/smscallback", method = RequestMethod.POST)
	public void smsCallback(@RequestBody MultiValueMap<String, String> map) {
		customerService.receiveOTP(map);
		webSocket.convertAndSend(TOPIC_DESTINATION,
				": Twilio has made a callback request! Here are the contents: " + map.toString());
	}

//	@RequestMapping(value = "/verifyOTP", method = RequestMethod.GET)
//	public ResponseEntity<String> verifyOTP(@RequestParam(name = "otp", required = true) int otp,
//			@RequestParam(name = "phoneNo", required = true) String phoneNo)
//			throws EcommerceException, ExecutionException {
//
//		String status = otpService.verifyOtp(phoneNo, otp);
//		return new ResponseEntity<>(status, HttpStatus.OK);
//
//	}

}
