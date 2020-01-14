package com.estafet.blockchain.demo.currency.converter.ms.container.tests;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentMessage;
import com.estafet.microservices.scrum.lib.commons.properties.PropertyUtils;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ITCurrencyConverterTest {

	BankPaymentConsumer topic = new BankPaymentConsumer();
	
	@Before
	public void before() {
		RestAssured.baseURI = PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI");
	}

	@After
	public void after() {
		topic.closeConnection();
	}

	@Test
	@DatabaseSetup("ITCurrencyConverterTest-data.xml")
	public void testGetExchangeRate() {
		get("exchange-rate/GBP").then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("currency", is("GBP"))
			.body("rate", is("1.26"));
	}

	@Test
	@DatabaseSetup("ITCurrencyConverterTest-data.xml")
	public void testGetExchangeRates() {
		get("exchange-rates").then()
		.statusCode(HttpURLConnection.HTTP_OK)
		.body("currency", hasItems("GBP", "USD"))
		.body("rate",  hasItems("1.26","0.845"));
	}

	@Test
	@DatabaseSetup("ITCurrencyConverterTest-data.xml")
	public void testUpdateExchangeRate() {
		given().contentType(ContentType.JSON)
			.body("{\"currency\": \"GBP\", \"rate\": \"41.645\" }")
			.when()
				.put("/exchange-rate")
			.then()
				.statusCode(HttpURLConnection.HTTP_OK)
				.body("currency", is("GBP"))
				.body("rate", is("41.645"));
	}
	
	@Test
	@DatabaseSetup("ITCurrencyConverterTest-data.xml")
	public void testCreateExchangeRate() {
		given().contentType(ContentType.JSON)
			.body("{\"currency\": \"LEV\", \"rate\": \"2.456\" }")
			.when()
				.post("/exchange-rate")
			.then()
				.statusCode(HttpURLConnection.HTTP_OK)
				.body("currency", is("LEV"))
				.body("rate", is("2.456"));
	}
	
	@Test
	@DatabaseSetup("ITCurrencyConverterTest-data.xml")
	public void testCurrencyConverter() {
		given().contentType(ContentType.JSON)
			.body("{\"currencyAmount\": \"55.6\", \"currency\": \"GBP\", \"walletAddress\": \"123456\", \"signature\": \"314249\", \"transactionId\": \"987654321\" }")
			.when()
				.post("/testCurrencyConverter")
			.then()
				.statusCode(HttpURLConnection.HTTP_OK)
				.body("amount", is("70.056"))
				.body("walletAddress", is("123456"))
				.body("signature", is("314249"))
				.body("walletAddress", is("987654321"));
	}

	@Test
	@DatabaseSetup("ITCurrencyConverterTest-data.xml")
	public void testConsumeNewWalletCurrencyConverterMessage() {
		CurrenyConverterTopicProducer.send("{\"currencyAmount\": \"305678\", \"currency\": \"USD\", \"walletAddress\": \"345678\", \"signature\": \"32423432\", \"transactionId\": \"2481632\" }");
		BankPaymentMessage message = topic.consume();
		assertEquals("258297.91", message.getAmount());
		assertEquals("345678", message.getWalletAddress());
		assertEquals("32423432", message.getSignature());
		assertEquals("2481632", message.getTransactionId());

	}
	
}
