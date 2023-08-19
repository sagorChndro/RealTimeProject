package com.sagor.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentInformation {
	@Column(name = "cardholder_name")
	private String cardHolderName;
	@Column(name = "card_number")
	private String cardNumber;
	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	@Column(name = "cvv")
	private String cvv;

}
