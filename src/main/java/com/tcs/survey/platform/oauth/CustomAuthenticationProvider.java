package com.tcs.survey.platform.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Authentication auth = null;
        List<GrantedAuthority> grantedAuths = null;
        String heartkidnumber = authentication.getName();
        String password = authentication.getCredentials().toString();

        grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        auth = new UsernamePasswordAuthenticationToken(heartkidnumber, password, grantedAuths);

        LOGGER.info("survey Number Access : " + heartkidnumber);

        return auth;
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
