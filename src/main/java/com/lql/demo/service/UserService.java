package com.lql.demo.service;

import com.lql.demo.auth.User;
import com.lql.demo.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final Keycloak adminKeycloak;
    private final KeycloakBuilder userKeyCloak;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println(user.toString());
        return user
                .orElseThrow(() -> new RuntimeException("user not found"));
    }


    public AccessTokenResponse grantToken(String username, String password) {
        return userKeyCloak.username(username)
                .password(password)
                .build().tokenManager().getAccessToken();

    }


    public Object createNewUser(String username, String password, String email, String firstname, String lastname) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEnabled(true);
        user.setGroups(List.of("user-group"));
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);

        user.setCredentials(List.of(credential));


        UsersResource users = adminKeycloak.realm("demo-realm").users();
        Response response = users.create(user);
        String createdId = CreatedResponseUtil.getCreatedId(response);

        return adminKeycloak.realm("demo-realm").users().get(createdId).toRepresentation();
    }

}
