package com.ttt.inquiry;

import static org.fest.assertions.api.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.ttt.inquiry.domain.Balance;

public class AppTest {
	private Client client;
	
	
	@ClassRule
	public static final DropwizardAppRule<InquiryConfiguration> RULE = new DropwizardAppRule<>(App.class, "config.yaml");
	
	@Before
	public void setUp(){
		client = new Client();
		
		client.addFilter(new HTTPBasicAuthFilter("test", "test"));
		
	}
	
	@Test
	public void createAndGetBalance(){
		//create user balance data
		WebResource inquiryBalanceResource = client.resource("http://localhost:8080/inquiry/balance");
		ClientResponse response = inquiryBalanceResource.post(ClientResponse.class,"userName=abcd&currency=BHT");
		
		assertThat(response.getStatus()).isEqualTo(201);
		
		//get balance
		WebResource getBalanceResource = client.resource("http://localhost:8080/inquiry/balance/abcd");
		Balance balance = getBalanceResource.get(Balance.class);
		
		assertThat(balance.getUserName()).isEqualTo("abcd");
		assertThat(balance.getCurrency()).isEqualTo("BHT");
	}
}
