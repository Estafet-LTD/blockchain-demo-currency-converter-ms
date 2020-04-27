package com.estafet.blockchain.demo.currency.converter.ms.container.tests;

import com.estafet.blockchain.demo.currency.converter.ms.model.ExchangeRate;
import com.estafet.blockchain.demo.currency.converter.ms.repository.ExchangeRateRepository;
import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentBlockChainMessage;
import com.estafet.openshift.boost.commons.lib.properties.PropertyUtils;
import com.estafet.openshift.boost.couchbase.lib.annotation.BucketSetup;
import com.estafet.openshift.boost.couchbase.lib.spring.CouchbaseTestExecutionListener;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, CouchbaseTestExecutionListener.class })
public class ITCurrencyConverterTest {

	BankPaymentConsumer topic = new BankPaymentConsumer();
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
	@Before
	public void before() {
		RestAssured.baseURI = PropertyUtils.instance().getProperty("CURRENCY_CONVERTER_SERVICE_URI");
		ExchangeRate exchangeRate = new ExchangeRate("GBP",1.26);
		exchangeRateRepository.save(exchangeRate);

		ExchangeRate exchangeRate1 = new ExchangeRate("USD",0.845);
		exchangeRateRepository.save(exchangeRate1);
	}

	@After
	public void after() {
		topic.closeConnection();
	}

	@Test
	@BucketSetup("ITCurrencyConverterTest.json")
	public void testGetExchangeRate() {
		get("exchange-rate/GBP").then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("currency", is("GBP"))
			.body("rate", equalTo(1.26f));
	}

	@Test
	@BucketSetup("ITCurrencyConverterTest.json")
	public void testGetExchangeRates() {
		get("exchange-rates").then()
		.statusCode(HttpURLConnection.HTTP_OK)
		.body("currency", hasItems("GBP", "USD"))
		.body("rate",  hasItems(1.26f,0.845f));
	}

	@Test
	@BucketSetup("ITCurrencyConverterTest.json")
	public void deleteExchangeRates() {
		delete("exchange-rates").then()
		.statusCode(HttpURLConnection.HTTP_OK)
		.body(is("Exchange Rates Deleted"));
	}
	
	@Test
	@BucketSetup("ITCurrencyConverterTest.json")
	public void testUpdateExchangeRate() {
		given().contentType(ContentType.JSON)
			.body("{\"currency\": \"GBP\", \"rate\": 41.645 }")
			.when()
				.put("/exchange-rate")
			.then()
				.statusCode(HttpURLConnection.HTTP_OK)
				.body("currency", is("GBP"))
				.body("rate", equalTo(41.645f));
	}
	
	@Test
	@BucketSetup("ITCurrencyConverterTest.json")
	public void testCreateExchangeRate() {
		given().contentType(ContentType.JSON)
			.body("{\"currency\": \"LEV\", \"rate\": 2.456 }")
			.when()
				.post("/exchange-rate")
			.then()
				.statusCode(HttpURLConnection.HTTP_OK)
				.body("currency", is("LEV"))
				.body("rate", equalTo(2.456f));
	}
	
	@Test
	@BucketSetup("ITCurrencyConverterTest.json")
	public void testCurrencyConverter() {
		given().contentType(ContentType.JSON)
			.body("{\"currencyAmount\": 55.6, \"currency\": \"GBP\", \"walletAddress\": \"123456\", \"signature\": \"314249\", \"transactionId\": \"987654321\" }")
			.when()
				.post("/test-currency-converter")
			.then()
				.statusCode(HttpURLConnection.HTTP_OK)
				.body("cryptoAmount", is(70))
				.body("walletAddress", is("123456"))
				.body("signature", is("314249"))
				.body("transactionId", is("987654321"));
	}

	@Test
	@BucketSetup("ITCurrencyConverterTest.json")
	public void testConsumeNewWalletCurrencyConverterMessage() {
		CurrenyConverterTopicProducer.send("{\"currencyAmount\": 305678, \"currency\": \"USD\", \"walletAddress\": \"345678\", \"signature\": \"32423432\", \"transactionId\": \"2481632\" }");
		BankPaymentBlockChainMessage message = topic.consume();
		assertEquals(258298, message.getCryptoAmount());
		assertEquals("345678", message.getWalletAddress());
		assertEquals("32423432", message.getSignature());
		assertEquals("2481632", message.getTransactionId());

	}
	
}
