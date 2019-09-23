package com.yhkj.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean buildAFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(6);
        filterRegistrationBean.setFilter(new WebContentFilter());
        filterRegistrationBean.setName("filter1");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
