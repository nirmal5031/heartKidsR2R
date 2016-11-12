package com.tcs.survey.platform.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Date;

@Configuration
public class OAuth2ServerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2ServerConfiguration.class);

    private static final String RESOURCE_ID = "restservice";

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

        @Autowired
        @Qualifier("tokenStore")
        public TokenStore tokenStore;

        @Autowired
        @Qualifier("dataSource")
        public DataSource dataSource;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            // @formatter:off
            endpoints.tokenStore(tokenStore).authenticationManager(
                    this.authenticationManager);
            // @formatter:on
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients)
                throws Exception {
            clients.jdbc(dataSource);
        }

    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends
            ResourceServerConfigurerAdapter {

        @Autowired
        @Qualifier("tokenStore")
        public TokenStore tokenStore;
        @Autowired
        @Qualifier("dataSource")
        public DataSource dataSource;
        @Autowired
        private CustomAuthenticator customFilter = new CustomAuthenticator();

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {

            // @formatter:off
            resources.resourceId(RESOURCE_ID)
                    .authenticationManager(customFilter).tokenStore(tokenStore);
            // @formatter:on
        }


        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.authorizeRequests()
                    .antMatchers("/survey/deleteadminuser/**")
                    .authenticated()
                    .antMatchers("/survey/fetchadminuser/**")
                    .authenticated()
                    .antMatchers("/survey/getuserdetails/**")
                    .authenticated()
                    .antMatchers("/survey/createadminuser")
                    .authenticated()
                    .antMatchers("/survey/deleterecord/**")
                    .authenticated()
                    .antMatchers("/survey/tokenvalidate")
                    .authenticated()
                    .antMatchers("/token/revoke")
                    .permitAll()
                    .and()
                    .addFilterAfter(customFilter,
                            AbstractPreAuthenticatedProcessingFilter.class);
        }

    }

    @Component
    protected static class CustomAuthenticator extends
            OAuth2AuthenticationManager implements Filter {

        @Autowired
        @Qualifier("tokenStore")
        private TokenStore tokenStore;

        @Autowired
        @Qualifier("dataSource")
        private DataSource dataSource;

        public void doFilter(ServletRequest request, ServletResponse response,
                             FilterChain filterChain) throws ServletException, IOException {

            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            if (authentication instanceof OAuth2Authentication) {

                OAuth2Authentication oAuth = (OAuth2Authentication) authentication;

                if (tokenStore != null) {

                    OAuth2AccessToken accessToken = tokenStore
                            .getAccessToken(oAuth);

                    DefaultOAuth2AccessToken df = new DefaultOAuth2AccessToken(
                            accessToken);

                    df.setExpiration(new Date(System.currentTimeMillis()
                            + (1800 * 1000)));

                    tokenStore.storeAccessToken(df, oAuth);
                }

            }

            filterChain.doFilter(request, response);
        }

        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            try {
                return super.authenticate(authentication);
            } catch (Exception e) {
                return new CustomAuthentication(authentication.getPrincipal(),
                        authentication.getCredentials());
            }
        }

        // @Override
        public void destroy() {
        }

        // @Override
        public void init(FilterConfig arg0) throws ServletException {

        }

        @Override
        public void afterPropertiesSet() {
        }

        @SuppressWarnings("serial")
        protected static class CustomAuthentication extends
                PreAuthenticatedAuthenticationToken {

            public CustomAuthentication(Object principal, Object credentials) {
                super(principal, credentials);
            }

        }

    }

}
