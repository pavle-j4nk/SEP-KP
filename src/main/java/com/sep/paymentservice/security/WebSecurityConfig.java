package com.sep.paymentservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RequestMatcher notForwarded = r -> r.getHeader("X-Forwarded-Proto") != null;
        http.requiresChannel().requestMatchers(notForwarded).requiresSecure();

        http.authorizeRequests().anyRequest().permitAll();

        http.csrf().disable();
    }

}
