package org.yesidl12.centinelaseguraltda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.yesidl12.centinelaseguraltda.security.jwt.JwtAuthFilter;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity 
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/pagos/**").hasAnyRole("ADMINISTRADOR", "CONTADOR", "ASESOR_COMERCIAL")
                .requestMatchers("/api/reportes/**").hasAnyRole("ADMINISTRADOR", "SUPERVISOR")
                .requestMatchers("/h2-console/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html", true)
                .failureUrl("/login.html?error=true")
                .permitAll()
            )
            .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }))
            .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login.html"))
            .headers(headers -> headers.frameOptions(f -> f.sameOrigin()))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
