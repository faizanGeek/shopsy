package com.luv2code.ecommerce.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.luv2code.ecommerce.dto.PaymentDTO;

public class PaymentUtil {

	private static final String paymentKey = "cvOPr5oC";

	private static final String paymentSalt = "G21VDTgjsS";

	private static final String sUrl = "N/A";

	private static final String fUrl = "N/A";

	public static PaymentDTO populatePaymentDetail(PaymentDTO paymentDetail) {
		String hashString = "";
		Random rand = new Random();
		String randomId = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
		String txnId = "Dev" + hashCal("SHA-256", randomId).substring(0, 12);
		paymentDetail.setTxnId(txnId);
		String hash = "";
		// String otherPostParamSeq = |||||||||||
		// "phone|surl|furl|lastname|curl|address1|address2|city|state|country|zipcode|pg";
		String hashSequence = "key|txnid|amount|productinfo|name|email|||||||||||";
		hashString = hashSequence.concat(paymentSalt);
		System.out.println(hashString);
		hashString = hashString.replace("key", paymentKey);
		hashString = hashString.replace("txnid", txnId);
		hashString = hashString.replace("amount", paymentDetail.getAmount());
		hashString = hashString.replace("productinfo", paymentDetail.getProductInfo());
		hashString = hashString.replace("name", paymentDetail.getName());
		hashString = hashString.replace("email", paymentDetail.getEmail());
		hashString = hashString.replace("salt", paymentSalt);
		System.out.println(hashString);

		hash = hashCal("SHA-512", hashString);
		paymentDetail.setKey(paymentKey);
		paymentDetail.setHash(hash);
		paymentDetail.setFUrl(fUrl);
		paymentDetail.setSUrl(sUrl);
		paymentDetail.setKey(paymentKey);
		return paymentDetail;
	}

	public static String hashCal(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
		}
		return hexString.toString();
	}

}
