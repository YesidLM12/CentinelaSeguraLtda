package org.yesidl12.centinelaseguraltda.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration 
@EnableJpaAuditing 
public class JpaAuditingConfig {

////    @Bean
////    public Optional<String> auditorProvider(){
////        Authentication autentication =
////            SecurityContextHolder.getContext()
////                .getAuthentication();
////
////        if (autentication != null || !autentication.isAuthenticated() || autentication.getPrincipal() == null) {
////            return Optional.empty();
////        }
////
////        return Optional.of(autentication.getName());
//
//    }


}
