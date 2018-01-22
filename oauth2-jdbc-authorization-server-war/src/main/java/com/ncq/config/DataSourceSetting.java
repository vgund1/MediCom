package com.ncq.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * Created by quynh on 11/10/2017.
 */
@Configuration
@PropertySource(value = { "classpath:persistence.properties" })
public class DataSourceSetting {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
    	JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
  		DataSource dataSource = dataSourceLookup.getDataSource("java:comp/env/jdbc/LocalDatabaseName");

    	
    	/* final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));*/
        return dataSource;
    }
}
