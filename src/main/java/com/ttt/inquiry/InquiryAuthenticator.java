package com.ttt.inquiry;

import org.skife.jdbi.v2.DBI;

import com.google.common.base.Optional;
import com.ttt.inquiry.dao.UserDao;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class InquiryAuthenticator implements Authenticator<BasicCredentials, Boolean>{
	private final UserDao userDao;
	
	public InquiryAuthenticator(DBI dbi) {
		userDao = dbi.onDemand(UserDao.class);
	}
	
	@Override
	public Optional<Boolean> authenticate(BasicCredentials arg0)
			throws AuthenticationException {
		boolean validUser = (userDao.getUser(arg0.getUsername(), arg0.getPassword()) == 1);
		
		if(validUser){
			return Optional.of(true);
		}
		
		return Optional.absent();
	}

}
