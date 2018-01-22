package com.ncq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * Created by quynh on 11/18/2017.
 */
@Configuration
@ComponentScan(basePackages = {"com.ncq"})
public class OauthSetting {

    @Autowired
    DataSourceSetting dataSourceSetting;

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceSetting.dataSource());
        //jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        //String str1 = encoder.encode("secret-key-for-api1");
        return encoder;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSourceSetting.dataSource());
    }
}
