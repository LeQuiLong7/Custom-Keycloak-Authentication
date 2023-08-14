package com.lql.demo.auth;

import com.lql.demo.filter.AdjustAuthenticationInfoFilter;
import com.lql.demo.filter.CorsFilterConfig;
import com.lql.demo.filter.CustomAuthenticationFailureHandler;
import com.lql.demo.filter.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CorsFilterConfig corsFilterConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .addFilterBefore(corsFilterConfig, ChannelProcessingFilter.class)
                    .addFilterAfter(new AdjustAuthenticationInfoFilter(), BearerTokenAuthenticationFilter.class)
                    .authorizeHttpRequests(req -> req.requestMatchers( "/auth/**").permitAll()
                                                    .anyRequest().authenticated())
                    .oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()))
                    .build();
    }

}
