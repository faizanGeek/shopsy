package com.luv2code.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.luv2code.ecommerce.config.JwtUtil;
import com.luv2code.ecommerce.dto.AddressDTO;
import com.luv2code.ecommerce.dto.CustomerDTO;
import com.luv2code.ecommerce.dto.SmsDTO;
import com.luv2code.ecommerce.entity.Address;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.exception.EcommerceException;
import com.luv2code.ecommerce.repository.AddressRepository;
import com.luv2code.ecommerce.repository.CustomerRepository;
import com.luv2code.ecommerce.util.Mapping;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	private Mapping mapping;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private Environment environment;

	LoadingCache<String, String> otpCache;

	public String registerCustomer(CustomerDTO customerDTO) throws EcommerceException {
		// TODO Auto-generated method stub

		String phoneNo = null;

		boolean isPhoneNotAvailable = customerRepository.findById(customerDTO.getPhoneNo()).isEmpty();

		if (isPhoneNotAvailable) {
			Customer customer = new Customer();
			customer = mapping.getCustomerEntity(customerDTO);
			customerRepository.save(customer);
		}
		return "saved";
	}

	public CustomerDTO getCustomerDetail(String token) {
		// TODO Auto-generated method stub
		String email = jwtUtil.extractUsername(token);
		Customer customer = customerRepository.findById(email).get();
		CustomerDTO customerDTO = new CustomerDTO();
		if (customer != null) {
			customerDTO = mapping.getCustomerDTO(customer);
		}
		return customerDTO;
	}

	public String addAddress(AddressDTO addressDTO) {
		// TODO Auto-generated method stub
		// List<Address> addressList = customerRepository.fin
		Address address = addressRepository.save(mapping.getAddressEntity(addressDTO));
		if (address == null) {

		}

		return "Address added";
	}

	public List<AddressDTO> getAddresses(String emailId) {
		// TODO Auto-generated method stub
		List<Address> addressList = addressRepository.findByCustomerPhoneNo(emailId);
		if (addressList.isEmpty()) {

		}
		List<AddressDTO> addressDTOList = new ArrayList<>();
		addressList.forEach(address -> {
			addressDTOList.add(mapping.getAddressDTO(address));
		});
		return addressDTOList;
	}

	public String updateAddress(AddressDTO addressDTO) {
		// TODO Auto-generated method stub
		// Address address = addressRepository.findById(addressDTO.getId()).get();
		Address address = addressRepository.save(mapping.getAddressEntity(addressDTO));

		return "updated";
	}

	public String deleteAddress(AddressDTO addressDTO) {
		// TODO Auto-generated method stub
		addressRepository.delete(mapping.getAddressEntity(addressDTO));
		return "deleted";
	}

	public void sendOTP(SmsDTO smsDTO) throws ExecutionException {
		long min = 100000;
		long max = 999999;
		long OTP = (long) (Math.random() * (max - min + 1) + min);
		String ACCOUNT_SID = environment.getProperty("TWILIO_ACCOUNT_SID");
		String AUTH_TOKEN = environment.getProperty("TWILIO_AUTH_TOKEN");
		String FROM_NUMBER = environment.getProperty("TWILIO_PHONE_NUMBER");

		this.otpCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.HOURS)
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String phoneNo) throws Exception {
						return Long.toString(OTP);
					}
				});
		otpCache.getUnchecked(smsDTO.getTo());

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		String msg = "Your OTP - " + OTP
				+ " please verify this OTP in your Application by Er Prince kumar Technoidentity.com";
		Message message = Message.creator(new PhoneNumber(smsDTO.getTo()), new PhoneNumber(FROM_NUMBER), msg).create();
		System.out.println("here is my id:" + message.getSid());// Unique resource ID created to manage this transaction

	}

	public void receiveOTP(MultiValueMap<String, String> map) {

	}

	public String verifyOTP(String phoneNo) throws EcommerceException, ExecutionException {
		// TODO Auto-generated method stub
		System.out.println(this.otpCache.get(phoneNo));
		// .if (this.otpCache.getIfPresent(phoneNo) == null)
		// throw new EcommerceException("Verification failed");

		return "verification Success";
	}
}
