package com.pizzerialavera.e_commerce.security;

import com.pizzerialavera.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Enable H2 console access
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/documentation", "/api-docs").permitAll() // Allow access to login
                        .requestMatchers("/post_products.html", "/get_products.html", "/post_customers.html", "/get_customers.html")
                        .hasRole("ADMIN") // Restrict access to ADMIN
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/index.html", true)) // Customize form login
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout")) // Customize logout
                .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }
}
