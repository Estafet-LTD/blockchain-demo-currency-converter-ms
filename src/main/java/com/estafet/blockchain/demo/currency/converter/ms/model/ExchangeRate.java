package com.estafet.blockchain.demo.currency.converter.ms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EXCHANGE_RATE")
public class ExchangeRate {

	@Id
	@Column(name = "CURRENCY", nullable = false)
	private String currency;
	
	@Column(name = "RATE", nullable = false)
	private Double rate;
	
	public double convert(double amount) {
		return amount * rate;
	}
	
}
