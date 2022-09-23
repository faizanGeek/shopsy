package com.luv2code.ecommerce.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.dto.SmsDTO;
import com.luv2code.ecommerce.entity.AuthenticationWithOtp;
import com.luv2code.ecommerce.exception.EcommerceException;
import com.luv2code.ecommerce.repository.AuthenticationWithOtpRepository;
import com.twilio.Twilio;

@Service
public class OtpService {

	@Autowired
	private Environment environment;

	@Autowired
	AuthenticationWithOtpRepository authOtp;

	public int sendOTP(SmsDTO smsDTO) throws InterruptedException {

		int min = 100000;
		int max = 999999;
		int OTP = (int) (Math.random() * (max - min + 1) + min);
		String ACCOUNT_SID = environment.getProperty("TWILIO_ACCOUNT_SID");
		String AUTH_TOKEN = environment.getProperty("TWILIO_AUTH_TOKEN");
		String FROM_NUMBER = environment.getProperty("TWILIO_PHONE_NUMBER");
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		String msg = "Your OTP - " + OTP + " please verify this OTP in your Application by Shopperr.";
//		Message message = Message.creator(new PhoneNumber("+91" + smsDTO.getTo()), new PhoneNumber(FROM_NUMBER), msg)
//				.create();

		AuthenticationWithOtp auth = new AuthenticationWithOtp();

		auth.setPhoneNo(smsDTO.getTo());
		auth.setOtp(String.valueOf(OTP));
		auth.setTime(LocalDateTime.now());

		authOtp.save(auth);

//		System.out.println("here is my id:" + message.getSid());
		return OTP;
	}

	// This method is used to return the OPT number against Key->Key values is
	// username
	public Boolean verifyOtp(String phoneNo, String clietnOtp) throws ExecutionException, EcommerceException {

		System.out.println(phoneNo);
		Boolean isAvailable;
		isAvailable = authOtp.findById(phoneNo).isPresent();
		if (!isAvailable) {
			throw new EcommerceException("OTP_Expired");
		}
		AuthenticationWithOtp auth = authOtp.findById(phoneNo).get();

		long seconds = ChronoUnit.SECONDS.between(auth.getTime(), LocalDateTime.now());

		System.out.println(seconds);
		Boolean status;
		if (seconds <= 600l && auth.getOtp().equals(clietnOtp)) {
			status = true;
		} else {
			if (seconds > 600l) {

				throw new EcommerceException("OTP_Expired");
			}
			throw new EcommerceException("Not_Valid_OTP");
		}
		authOtp.delete(auth);
		return status;
	}

	// This method is used to clear the OTP catched already
	public void clearOTP(String key) {
//		otpCache.invalidate(key);
	}

}
