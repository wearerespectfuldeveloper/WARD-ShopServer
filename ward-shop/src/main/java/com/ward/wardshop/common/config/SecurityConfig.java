package com.ward.wardshop.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private boolean isLocalMode() {
        String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "local";
        return profile.equals("local");
    }

    private void setLocalMode(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
    }

    private void setProdMode(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/simple", "/swagger*/**").permitAll()
                .antMatchers("/**").authenticated();
    }
}
