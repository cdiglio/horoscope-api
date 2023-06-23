package com.cd.horoscope.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Configure endpoint security
 */
@Configuration
public class HoroscopeSecurity {

    /**
     * Configures the different access to various endpoints
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/openai/**").hasRole("ADMIN") //openai endpoint can only be used by admins
                        .requestMatchers("/horoscope/**").permitAll() //all horoscope endpoints besides using DELETE dont need auth
                        .requestMatchers(HttpMethod.DELETE,"/horoscope/daily").hasRole("ADMIN") //DELETE daily horoscope endpoint needs auth


        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    /**
     * Call to members and role tables to determine is user is authenticated and has correct role
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        manager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?"
        );

        manager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?"
        );

        return manager;
    }





}
