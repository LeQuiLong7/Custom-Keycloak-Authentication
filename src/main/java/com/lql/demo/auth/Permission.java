package com.lql.demo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements GrantedAuthority {
    private String permission;

    @Override
    public String getAuthority() {
        return permission;
    }
}
