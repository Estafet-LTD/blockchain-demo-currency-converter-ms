package com.estafet.blockchain.demo.ms.currency.converter.ms.ms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EXCHANGE_RATE")
public class ExchangeRate {

	@Id
	@SequenceGenerator(name = "EXCHANGE_RATE_ID_SEQ", sequenceName = "EXCHANGE_RATE_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXCHANGE_RATE_ID_SEQ")
	@Column(name = "EXCHANGE_RATE_ID")
	private Integer id;
	
	@Column(name = "FROM_CURRENCY", nullable = false)
	private String fromCurrency;
	
	@Column(name = "TO_CURRENCY", nullable = false)
	private String toCurrency;
	
	@Column(name = "RATE", nullable = false)
	private Double rate;
	
	public double convert(double fromAmount) {
		return fromAmount * rate;
	}

	
}
