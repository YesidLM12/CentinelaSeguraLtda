package org.yesidl12.centinelaseguraltda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**","/h2-console/**").permitAll()
                .requestMatchers("/pagos/**").hasAnyRole("ADMINISTRADOR", "CONTADOR", "ASESOR_COMERCIAL")
                .requestMatchers("/api/reportes/**").hasAnyRole("ADMINISTRADOR", "SUPERVISOR")
//                .requestMatchers("/h2-console/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    boolean esAdmin = authentication.getAuthorities().stream().anyMatch(a -> "ROLE_ADMINISTRADOR".equals(a.getAuthority()));
                    // TODO => modificar "/usuarios" por la pagina principal
                    response.sendRedirect(esAdmin ? "/usuarios" : "/login");
                })
                .failureHandler((request, response, exception) -> {
                   System.out.println("LOGIN FALLIDO: " + exception.getClass().getSimpleName() + " - " + exception.getMessage());
                   response.sendRedirect("/login?error=true");
                })
                .permitAll()
            )
            .logout(l -> l
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"))
            .headers(headers -> headers.frameOptions(f -> f.sameOrigin()))
            .csrf(c -> c.ignoringRequestMatchers("/h2-console/**"));
            
        return http.build();
    }
}
