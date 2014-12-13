package com.ttt.inquiry.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Balance {
	private final Long id;
	
	@NotBlank
	@Length(min=2, max = 25)
	private final String userName;
	
	@NotNull
	private final BigDecimal balance;
	
	private final String currency;
	
	public Balance(){
		this.id = new Long(0);
		this.userName = null;
		this.balance = new BigDecimal(0);
		this.currency = null;
	}

	public Balance(Long id, String userName, BigDecimal balance, String currency) {
		super();
		this.id = id;
		this.userName = userName;
		this.balance = balance;
		this.currency = currency;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getCurrency() {
		return currency;
	}
}
