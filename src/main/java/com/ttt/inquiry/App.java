package com.ttt.inquiry;

import io.dropwizard.Application;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilderSpec;
import com.sun.jersey.api.client.Client;
import com.ttt.inquiry.health.AppHealthCheck;
import com.ttt.inquiry.resources.ClientResource;
import com.ttt.inquiry.resources.InquiryBalanceResource;


public class App extends Application<InquiryConfiguration>{
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	@Override
	public void initialize(Bootstrap<InquiryConfiguration> arg0) {		
	}

	@Override
	public void run(InquiryConfiguration config, Environment environment) throws Exception {
		LOGGER.info("Method App#run() called");
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "mysql");
		final Client client = new JerseyClientBuilder(environment).build("Client");
		
		CachingAuthenticator<BasicCredentials, Boolean> authenticator = new CachingAuthenticator<>(environment.metrics(),new InquiryAuthenticator(jdbi), CacheBuilderSpec.parse("maximumSize=10000,expireAfterAccess=10m"));
		
		environment.jersey().register(new InquiryBalanceResource(jdbi,environment.getValidator()));
		environment.jersey().register(new ClientResource(client));
		environment.jersey().register(new BasicAuthProvider<>(authenticator, "Realm"));
		
		environment.healthChecks().register("Healthy check ", new AppHealthCheck(client));
	}
	
	public static void main(String[] args)throws Exception{
        new App().run(args);
    }
	
}
