package com.lql.demo;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@SpringBootApplication
@EnableCaching
@EnableMongoHttpSession
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public Keycloak keycloak() {
        Keycloak build = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8000")
                .realm("demo-realm") // Replace with your Keycloak realm name
                .clientId("demo-client")
                .clientSecret("O4IzIDaqpwrZUuCX3fu7gzHRbLCVDyHn")
                .grantType("client_credentials")
                .build();


        return build;
    }
    @Bean
    public KeycloakBuilder keycloak2 () {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8000")
                .realm("demo-realm")
                .clientId("react-client")
                .clientSecret("65ZhWEkm6COgGImGtJwU1O31PQDTsQVn")
                .grantType("password");
    };


}
