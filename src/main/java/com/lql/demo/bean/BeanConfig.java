package com.lql.demo.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BeanConfig {

    private final UserDetailsService myUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        DaoAuthenticationProvider provider = new MyAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(myUserDetailService);


        return provider;
    }






//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource sources = new UrlBasedCorsConfigurationSource();
//        sources.registerCorsConfiguration("/**", configuration);
//
//        return (CorsConfigurationSource) sources;
//    }

//    @Bean
//    public CommandLineRunner runner(UserRepoImpl userRepo) {
//        return args -> {
//            Permission read = new Permission("READ");
//            User user1 = new User(null, "long", passwordEncoder().encode("a"), List.of(read));
//            User user2 = new User(null, "nam",passwordEncoder().encode("a"), List.of(read));
//
//            userRepo.saveUser(user1);
//            userRepo.saveUser(user2);
//        };
    }

