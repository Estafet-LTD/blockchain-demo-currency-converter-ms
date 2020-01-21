package com.estafet.blockchain.demo.currency.converter.ms;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConverterTest {

	@Test
	public void roundUSD() {
		double amount = 305678;
		int i = (int) Math.round(amount * 0.845);
		System.out.println("USD amount = "+i);

	}

	@Test
	public void roundGBP() {
		double amount = 55.6;
		int i = (int) Math.round(amount * 1.26);
		System.out.println("GBP amount = "+i);

	}

	@Test
	public void roundBGN() {
		double amount = 55.6;
		int i = (int) Math.round(amount * 2.456);
		System.out.println("GBP amount = "+i);

	}

}
