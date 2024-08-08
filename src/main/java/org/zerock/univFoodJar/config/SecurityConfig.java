package org.zerock.univFoodJar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
//@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{



        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers( "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/upload/**", "/display/**").permitAll()
                        .requestMatchers( "/univFood", "/univFood/", "/univFood/list", "/univFood/read", "/reviews/**").permitAll()
                        // 괄호내의 경로로 들어오면 뒤의 권한으로 권한 심사, permitAll 이므로 모두 허용
                        .requestMatchers("/univFood/register", "/univFood/modify", "/univFood/delete").hasRole("USER")
                        .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated())
        .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/univFood/list", true)
                        .permitAll()
        ).logout(logout -> logout.permitAll());
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("hj0")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
