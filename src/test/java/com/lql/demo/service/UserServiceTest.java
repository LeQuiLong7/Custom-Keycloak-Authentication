package com.lql.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lql.demo.repository.UserRepository;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
//@RestClientTest(UserService.class)
class UserServiceTest {

//    @MockBean
    private RestTemplate restTemplate = new RestTemplate();


    @Test
    void createNewUser() throws JsonProcessingException {

        String url = "http://localhost:8000/realms/demo-realm/protocol/openid-connect/token";
        MultiValueMap<String, String> adminInfo = new LinkedMultiValueMap<>();

        adminInfo.add("grant_type", "client_credentials");
        adminInfo.add("client_id", "demo-client");
        adminInfo.add("client_secret", "O4IzIDaqpwrZUuCX3fu7gzHRbLCVDyHn");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(adminInfo, headers);

        String body = restTemplate.postForEntity(url, requestEntity, String.class).getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.readTree(body).get("access_token").asText());
    }

    @Test
    void jsonTest() {
        String username = "long";
        String password = "longg";
        String json  = String.format(
                """ 
                    {
                    "username": "%s",
                    "credentials": [{ "type": "password", "value": "%s", "temporary": false }]
                    }
                """, username, password);
        System.out.println(json);
    }

    @Test
    void WebClientTest() {
//        Logger logger = LoggerFactory.getLogger(UserService.class);
        WebClient.Builder webClient = webClientBuilder();
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxeXVHNXg5R3BmcVdFUjlJdUJmZWxXNHRiMVJxS3VNR0E0NDRpZ0N3bzJNIn0.eyJleHAiOjE2OTE0NTgyMTYsImlhdCI6MTY5MTQ1NzkxNiwianRpIjoiYjZkZWMxM2YtZDliNS00ZTlmLTgzOWItMzI5YWI3YmYzYmE1IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDAwL3JlYWxtcy9kZW1vLXJlYWxtIiwiYXVkIjpbInJlYWxtLW1hbmFnZW1lbnQiLCJhY2NvdW50Il0sInN1YiI6IjljYzFhM2I1LWUzMTctNDcwMS05MzExLTljOGM0MWE0YjAzNiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImRlbW8tY2xpZW50IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLWRlbW8tcmVhbG0iLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyJdfSwiZGVtby1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SG9zdCI6IjE3Mi4xNy4wLjEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtZGVtby1jbGllbnQiLCJjbGllbnRBZGRyZXNzIjoiMTcyLjE3LjAuMSIsImNsaWVudF9pZCI6ImRlbW8tY2xpZW50In0.YUi-5VGyZJ3CoxQhWWvjak-6yQACkgcvVh7Yl0PeILiYpAf4O-PCkTO8p9p8gtaBH2iRNTs3vlo6466mgrqC7t2N78vqYDt2dPiiEqdNVbvNhsRkdu1i_WGdKhHCUzDTIuruGEEmHGTe39i8V0UBZ6v4zJoBDEvV5jbutMAcm3ZDfi1Y7o6I1r7XU41I4yJdwkZK7D3mJyFvP6wB2X-GmImxcJ2CE70_ay5PUjd6hAZE9YJ5b56fzWTondtGN9WCWUUmE8VOtWube61T4Cq3pPhBx8hZaMApjF4UgGfsxvm5p9kMr_mZdlYwP8LfLMxvbgKviq70__Hq4cItDPJasg";
        String username = "abc";
        String password = "abc";
        String json  = String.format(
                """ 
                    {
                    "username": "%s",
                    "credentials": [{ "type": "password", "value": "%s", "temporary": false }]
                    }
                """, username, password);
        String res  = webClient.build()
                .post()
                .uri("http://localhost:8000/admin/realms/demo-realm/users")
                .header("Authorization", String.format("Bearer %s", token))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class).block();

//        System.out.println(authorization);
    }


    public WebClient.Builder webClientBuilder() {
        return WebClient
                .builder()
                .filter(logRequest())
                .filter(logResponse());
    }


    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            System.out.printf("Request: %s %s", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> System.out.printf("%s=%s", name, value)));
            return next.exchange(clientRequest);
        };
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.printf("Response: %s", clientResponse.headers().asHttpHeaders().get("status"));
            return Mono.just(clientResponse);
        });

    }


    @Test
    void keycloakTest() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8000")
                .realm("demo-realm") // Replace with your Keycloak realm name
                .clientId("demo-client")
                .clientSecret("O4IzIDaqpwrZUuCX3fu7gzHRbLCVDyHn")
                .grantType("client_credentials")
//                .username("admin") // Replace with your Keycloak admin username
//                .password("admin") // Replace with your Keycloak admin password
                .build();
        UserRepresentation user = new UserRepresentation();
        user.setUsername("longg");
        user.setEnabled(true);
        UsersResource users = keycloak.realm("demo-realm").users();

            Response response = users.create(user);


    }

    @Test
    void keycloakTest2() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8000")
                .realm("demo-realm") // Replace with your Keycloak realm name
                .clientId("react-client")
                .clientSecret("65ZhWEkm6COgGImGtJwU1O31PQDTsQVn")
                .grantType("password")
                .username("longg") // Replace with your Keycloak admin username
                .password("longgs") // Replace with your Keycloak admin password
                .build();


        AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();
        System.out.println(accessToken);

    }
}