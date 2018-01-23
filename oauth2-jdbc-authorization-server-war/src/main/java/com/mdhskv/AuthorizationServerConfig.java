package com.mdhskv;

import com.mdhskv.config.DataSourceSetting;
import com.mdhskv.config.OauthSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


/**
 * Created by quynh on 11/10/2017.
 */
@Configuration
@EnableAuthorizationServer
@ComponentScan(basePackages = {"com.mdhskv"})
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSourceSetting dataSourceSetting;

    @Autowired
    private OauthSetting oauthSetting;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    //@Qualifier(value = "customUserDetailService")
    private CustomUserDetailService userDetailService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                // check the client_secret under "encode" mode. (p/s: the client_secret must be encoded when storing into database)
                .passwordEncoder(oauthSetting.passwordEncoder());
                // Authenticating the client using the form parameters instead of basic auth
                //.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSourceSetting.dataSource());
        
                // check the client_id under "encode" mode. (p/s: the client_id must be encoded when storing into database)
                //.passwordEncoder(dataSourceSetting.passwordEncoder());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService)
                .tokenStore(oauthSetting.tokenStore())
                .reuseRefreshTokens(false); // it will return a new refresh_token value when calling oauth endpoint to refresh access token
    }
}
