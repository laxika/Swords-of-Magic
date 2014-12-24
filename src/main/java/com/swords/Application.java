package com.swords;

import com.swords.filter.SessionLoginFilter;
import com.swords.interceptor.SessionLocaleChangeInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.Locale;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public FilterRegistrationBean filterSessionLoginBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SessionLoginFilter sessionFilter = new SessionLoginFilter();

        registrationBean.setFilter(sessionFilter);

        ArrayList<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/admin/*");

        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);

        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new SessionLocaleChangeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
