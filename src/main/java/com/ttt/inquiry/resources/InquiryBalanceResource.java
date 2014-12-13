package com.ttt.inquiry.resources;

import io.dropwizard.auth.Auth;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;

import com.ttt.inquiry.dao.BalanceDao;
import com.ttt.inquiry.domain.Balance;

@Path("/inquiry/balance")
@Produces(MediaType.APPLICATION_JSON)
public class InquiryBalanceResource {
	private final BalanceDao balanceDao;
	private final Validator validator;
	
	public InquiryBalanceResource(DBI jdbi,Validator validator) {
		balanceDao = jdbi.onDemand(BalanceDao.class);
		this.validator = validator;
	}
	
	@GET
	@Path("/{userName}")
	public Response getBalance(@PathParam("userName") String userName, @Auth Boolean isAuthenticated){
		Balance balance = balanceDao.getBalanceByUserName(userName);
		return Response.ok(balance).build();
	}
	
	@POST
	public Response createBalance(@FormParam("userName") String userName, @FormParam("currency") String currency,@Auth Boolean isAuthenticated) throws URISyntaxException{
		Balance balance = new Balance(null, userName, BigDecimal.ZERO, currency);
		System.out.println(userName);
		Set<ConstraintViolation<Balance>> violations = validator.validate(balance);
		
		if(violations.size() > 0){
			ArrayList<String> validationMessages = new ArrayList<String>();
			
			for(ConstraintViolation<Balance> violation : violations){
				validationMessages.add(violation.getPropertyPath().toString() +": " + violation.getMessage());
			}
			
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		
		long id = balanceDao.createBalance(userName, BigDecimal.ZERO, currency);
			
		return Response.created(new URI(String.valueOf(id))).build();		
	}
	
	@DELETE
	@Path("/{userName}")
	public Response deleteBalance(@PathParam("userName") String userName, @Auth Boolean isAuthenticated){
		balanceDao.deleteBalanceByUserName(userName);
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/{userName}")
	public Response updateBalance(@PathParam("userName") String userName, @FormParam("balance") String balance, @FormParam("currency") String currency, @Auth Boolean isAuthenticated){
		Balance bal = new Balance(null, userName, BigDecimal.ZERO, currency);
		
		Set<ConstraintViolation<Balance>> violations = validator.validate(bal);
		
		if(violations.size() > 0){
			ArrayList<String> validationMessages = new ArrayList<String>();
			
			for(ConstraintViolation<Balance> violation : violations){
				validationMessages.add(violation.getPropertyPath().toString() +": " + violation.getMessage());
				
			}
			
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		
		balanceDao.updateBalanceByUserName(userName, new BigDecimal(balance), currency);
		return Response.ok(balanceDao.getBalanceByUserName(userName)).build();
	}
}
