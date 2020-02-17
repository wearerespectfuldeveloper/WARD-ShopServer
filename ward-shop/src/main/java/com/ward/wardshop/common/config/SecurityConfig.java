package com.ward.wardshop.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (isLocalMode()) {
            setLocalMode(http);
        } else {
            setProdMode(http);
        }

        http.csrf().disable()
                .headers().frameOptions().disable();

        http.cors();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:8080",
                "http://ward-study.com",
                "http://www.ward-study.com"
                )
        );

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private boolean isLocalMode() {
        String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "local";
        return profile.equals("local");
    }

    private void setLocalMode(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();
    }

    private void setProdMode(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**", //개발 편의성을 위해 임시로 개방
                        "/simple",
                        "/v2/api-docs",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**")
                .permitAll()
                .anyRequest().authenticated();
    }
}
