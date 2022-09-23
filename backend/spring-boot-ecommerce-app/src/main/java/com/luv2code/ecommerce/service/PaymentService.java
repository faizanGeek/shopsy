package com.luv2code.ecommerce.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.config.PaymentUtil;
import com.luv2code.ecommerce.dto.PaymentCallback;
import com.luv2code.ecommerce.dto.PaymentDTO;
import com.luv2code.ecommerce.entity.Payment;
import com.luv2code.ecommerce.repository.PaymentRepo;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepo paymentRepository;

	public PaymentDTO proceedPayment(PaymentDTO paymentDetail) {
		PaymentUtil paymentUtil = new PaymentUtil();
		paymentDetail = paymentUtil.populatePaymentDetail(paymentDetail);
		savePaymentDetail(paymentDetail);
		return paymentDetail;
	}

//	public String payuCallback(PaymentCallback paymentResponse) {
//		String msg = "Transaction failed.";
//		Payment payment = paymentRepository.findByTxnId(paymentResponse.getTxnid());
//		if (payment != null) {
//			// TODO validate the hash
//			PaymentStatus paymentStatus = null;
//			if (paymentResponse.getStatus().equals("failure")) {
//				paymentStatus = PaymentStatus.Failed;
//			} else if (paymentResponse.getStatus().equals("success")) {
//				paymentStatus = PaymentStatus.Success;
//				msg = "Transaction success";
//			}
//			payment.setPaymentStatus(paymentStatus);
//			payment.setMihpayId(paymentResponse.getMihpayid());
//			payment.setMode(paymentResponse.getMode());
//			paymentRepository.save(payment);
//		}
//		return msg;
//	}
	public String payuCallback(PaymentCallback paymentCallback) {
		System.out.println(paymentCallback);
		// TODO Auto-generated method stub
		String msg = "FAILED";
		Payment payment = paymentRepository.findByTxnId(paymentCallback.getTxnId());
		if (payment != null) {
			if (paymentCallback.getStatus().equalsIgnoreCase("SUCCESS")) {
				payment.setPaymentStatus("SUCCESS");
				msg = "SUCCESS";
			} else if (paymentCallback.getStatus().equalsIgnoreCase("PAY_ON_DELIVERY")) {
				payment.setPaymentStatus("PAY_ON_DELIVERY");
				msg = "SUCCESS";
			} else {
				payment.setPaymentStatus("FAILURE");
			}
			payment.setPayId(paymentCallback.getPayId());
			payment.setMode(paymentCallback.getMode());
			payment.setEmail(paymentCallback.getEmail());
			paymentRepository.save(payment);
		}
		return msg;
	}

	private void savePaymentDetail(PaymentDTO paymentDetail) {
		Payment payment = new Payment();
		payment.setAmount(Double.parseDouble(paymentDetail.getAmount()));
		payment.setEmail(paymentDetail.getEmail());
		payment.setName(paymentDetail.getName());
		payment.setPaymentDate(new Date());
		payment.setPaymentStatus("Pending");
		payment.setPhoneNo(paymentDetail.getPhoneNo());
		payment.setProductInfo(paymentDetail.getProductInfo());
		payment.setTxnId(paymentDetail.getTxnId());
		paymentRepository.save(payment);
	}

}
