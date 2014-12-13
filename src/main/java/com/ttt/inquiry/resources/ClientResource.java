package com.ttt.inquiry.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.ttt.inquiry.domain.Balance;

@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
@Path("/client/")
public class ClientResource {
	private Client client;
	public ClientResource(Client client){
		this.client = client;
	}
	
	@GET
	@Path("showBalanceData")
	public String getBalanceData(@QueryParam("userName") String userName){
		WebResource inquiryBalanceResource = client.resource("http://localhost:8080/inquiry/balance/"+userName);
		Balance balance = inquiryBalanceResource.get(Balance.class);
		String output = "ID: "+ balance.getId() 
				+"\nUserName: " + userName 
				+ "\nBalance: " + balance.getBalance() 
				+ "\nCurrency: " + balance.getCurrency();
		
		return output;
	}
	
	@GET
	@Path("newBalance")
	public Response newBalance(@QueryParam("userName") String userName, @QueryParam("currency") String currency){
		WebResource inquiryBalanceResource = client.resource("http://localhost:8080/inquiry/balance");
		ClientResponse response = inquiryBalanceResource.post(ClientResponse.class,"userName="+userName+"&currency="+currency);
		
		if (response.getStatus() == 201) {
			return Response.status(302).entity("New Balance data was created successfully! The new balance data can be found at " + 
			response.getHeaders().getFirst("Location")).build();
		}
		
		return Response.status(422).entity(response.
		getEntity(String.class)).build();
		
	}
}
