package com.ttt.inquiry.health;

import com.codahale.metrics.health.HealthCheck;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AppHealthCheck extends HealthCheck{
	private final Client client;
	
	public AppHealthCheck(Client client) {
		super();
		this.client = client;
	}
	
	@Override
	protected Result check() throws Exception {
		WebResource inquiryBalanceResource = client.resource("http://localhost:8080/inquiry/balance");
		ClientResponse response = inquiryBalanceResource.post(ClientResponse.class,"userName=health&currency=health");
		
		if(response.getStatus() == 201){
			return Result.healthy();
		}
		
		return Result.unhealthy("unhealthy, resource can't be created");
	}
	
}
