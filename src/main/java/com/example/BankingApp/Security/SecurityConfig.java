package com.example.BankingApp.Security;

import com.example.BankingApp.Entity.User;
import com.example.BankingApp.Repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // 🔹 This method WILL be called after fixing loginProcessingUrl
    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> {

            System.out.println("Login attempt for: " + username);
            User user = repository.findByUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));

            System.out.println("DB Username: " + user.getUsername());
            System.out.println("DB Password: " + user.getPassword());

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().toString())   // MANAGER / CUSTOMER
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/page", "/css/**", "/js/**").permitAll()
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .requestMatchers("/customer/**").hasAnyRole("CUSTOMER","MANAGER")
                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/page")           // GET login page
                .loginProcessingUrl("/login") // POST login form
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {

                    boolean isManager = authentication.getAuthorities()
                            .stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));

                    if (isManager) {
                        response.sendRedirect("/manager");
                    } else {
                        response.sendRedirect("/customer");
                    }
                })
                .failureUrl("/page?error=true")
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/page")
        );

        return http.build();
    }
}
