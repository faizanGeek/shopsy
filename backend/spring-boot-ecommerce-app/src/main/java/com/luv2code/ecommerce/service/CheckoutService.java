package com.luv2code.ecommerce.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.dto.OrderDTO;
import com.luv2code.ecommerce.entity.Cart;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.repository.CartRepository;
import com.luv2code.ecommerce.repository.CustomerRepository;
import com.luv2code.ecommerce.repository.OrderRepository;
import com.luv2code.ecommerce.util.Mapping;

@Service
public class CheckoutService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private Mapping mapping;

	@Autowired
	private OrderRepository orderRepository;

	public String placeOrder(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		createOrder(orderDTO);
		createNewCart(orderDTO);

		return null;
	}

	private void createOrder(OrderDTO orderDTO) {
		String phoneNo = orderDTO.getCustomerPhoneNo();
		String orderNumber = generateOrderNumber(phoneNo);
		Order order = new Order();
		order.setOrderNumber(orderNumber);
		order.setOrderedCartId(orderDTO.getOrderedCartId());
		order.setDateCreated(new Date());
		//order.setShippingAddress(mapping.getAddressEntity(orderDTO.getShippingAddress()));
		order.setStatus("NEW");
		order.setPaymentThrough(orderDTO.getPaymentThrough());
		order.setDateOfDelivery(orderDTO.getDateOfDelivery());
		order.setTimeofDelivery(orderDTO.getTimeofDelivery());
		order.setCustomerPhoneNo(phoneNo);
		orderRepository.save(order);
	}

	private String generateOrderNumber(String phoneNo) {
		// TODO Auto-generated method stub
		Random rng = new Random();
		Integer code = rng.nextInt(90000) + 10000;
		String orderId = Integer.toString(code) + phoneNo.substring(5);

		return orderId;
	}

	private void createNewCart(OrderDTO orderDTO) {
		String phoneNo = orderDTO.getCustomerPhoneNo();
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findByPhoneNo(phoneNo);
		Cart newCart = new Cart();
		customer.setCustomerCart(newCart);
		customerRepository.save(customer);

	}

	/*
	 * @Autowired private CustomerRepository customerRepository;
	 * 
	 * @Transactional public PurchaseResponse placeOrder(Purchase purchase) {
	 */

	/*
	 * // retrieve the order info from dto Order order = purchase.getOrder();
	 * 
	 * // generate tracking number String orderTrackingNumber =
	 * generateOrderTrackingNumber();
	 * order.setOrderTrackingNumber(orderTrackingNumber);
	 * 
	 * // populate order with orderItems Set<OrderItem> orderItems =
	 * purchase.getOrderItems(); orderItems.forEach(item -> order.add(item));
	 * 
	 * // populate order with billingAddress and shippingAddress
	 * order.setBillingAddress(purchase.getBillingAddress());
	 * order.setShippingAddress(purchase.getShippingAddress());
	 * 
	 * // populate customer with order Customer customer = purchase.getCustomer();
	 * customer.add(order);
	 * 
	 * // save to the database customerRepository.save(customer);
	 * 
	 * // return a response PurchaseResponse purchaseResponse = new
	 * PurchaseResponse();
	 * purchaseResponse.setOrderTrackingNumber(orderTrackingNumber); if
	 * (orderTrackingNumber == null) throw new CatalogException(null); return
	 * purchaseResponse;
	 */

	/*
	 * }
	 * 
	 * private String generateOrderTrackingNumber() {
	 * 
	 * 
	 * // generate a random UUID number (UUID version-4) // For details see:
	 * https://en.wikipedia.org/wiki/Universally_unique_identifier // return
	 * UUID.randomUUID().toString();
	 * 
	 * }
	 */

}
