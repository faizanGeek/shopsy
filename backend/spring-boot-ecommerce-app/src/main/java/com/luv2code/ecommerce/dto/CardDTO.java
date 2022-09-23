package com.luv2code.ecommerce.dto;

import java.time.LocalDateTime;

import com.luv2code.ecommerce.entity.CardType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CardDTO {

	private Long cardId;

	private CardType cardType;

	private String cardNumber;

	private String cvv;

	private LocalDateTime expiryDate;

	private String nameOnCard;

	private String customerPhoneNo;
}
