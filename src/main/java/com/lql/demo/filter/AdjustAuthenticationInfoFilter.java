package com.lql.demo.filter;

import com.lql.demo.dto.CustomAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AdjustAuthenticationInfoFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }


//        JwtAuthenticationToken token = new JwtAuthenticationToken(
//                ((Jwt) authentication.getPrincipal()),
//                authentication.getAuthorities(),
//                );


        Jwt jwt = ((Jwt) authentication.getPrincipal());
        List<? extends GrantedAuthority> roles = getRoles(jwt);


        String name = jwt.getClaimAsString("name");
        CustomAuthentication auth = new CustomAuthentication(
                name == null ? jwt.getClaimAsString("preferred_username") : name,
                roles);


        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);

    }


    List<? extends GrantedAuthority> getRoles(Jwt jwt) {

        Map<String, Object> resource_access = jwt.getClaimAsMap("resource_access");
        List<String> roles = (List) ((Map) resource_access.get("react-client")).get("roles");

        return roles.parallelStream().map(SimpleGrantedAuthority::new).toList();
    }
}
