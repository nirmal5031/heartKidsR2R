package com.tcs.survey.platform.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class RevokeTokenController {
    public static final String ENDPOINT_ID = "token";
    private static final Logger LOGGER = LoggerFactory.getLogger(RevokeTokenController.class);

    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    private DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }

    @RequestMapping(value = "token/revoke", method = RequestMethod.DELETE)
    public void revoke(@RequestHeader(value = "Authorization") String authToken) {
        String token = authToken.substring(authToken.indexOf(" ")).trim();

        boolean success = tokenServices().revokeToken(token);
        if (!success) {
System.out.println("Access token is already invoked");        }

        LOGGER.info("Successfully revoked token");
    }

}
