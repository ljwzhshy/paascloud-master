package com.paascloud.provider.security;

import com.paascloud.security.server.PcResourceServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

@Configuration
@EnableResourceServer
public class SecurityConfigCore extends PcResourceServerConfig {
    @Resource
    private  MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    public void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        super.configure(http);
    }
}
