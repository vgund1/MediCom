package com.ncq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ncquynh on 11/14/2017.
 */
@Controller
public class Oauth2Controller {
    private static final String BEARER_AUTHENTICATION = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Autowired
    private TokenStore tokenStore;

    /**
     * Remove both access/refresh token using access token
     */
    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void revokeToken(HttpServletRequest request){

        String authHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (authHeader != null) {
            String tokenValue = authHeader.replace(BEARER_AUTHENTICATION, "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken); // remove access token
            tokenStore.removeRefreshToken(accessToken.getRefreshToken()); // remove refresh token
        }
    }
    

    
}
