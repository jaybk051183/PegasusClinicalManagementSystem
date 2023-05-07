package nl.novi.pegasusclinicalmanagementsystem.security;

import nl.novi.pegasusclinicalmanagementsystem.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/appointments/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/appointments").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/appointments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/appointments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/doctors/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/doctors").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/doctors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/doctors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/patients/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/patients").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/patients/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/patients/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/invoices/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/invoices/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/invoices").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/invoices/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/medical-records/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/medical-records/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/medical-records/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/medical-records/**").hasRole("ADMIN")
                .requestMatchers("/api/reports/**", "/api/statistics/**", "/upload", "/download/**").hasRole("ADMIN")
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}