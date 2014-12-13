package com.ttt.inquiry;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InquiryConfiguration extends Configuration{
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();
    
    
    public DataSourceFactory getDataSourceFactory() {
    	return database;
    }
}
