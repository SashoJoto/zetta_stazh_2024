package dev.zettalove.zettalove.security;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.keycloak.representations.idm.CredentialRepresentation.PASSWORD;

@Component
public class KeycloakProvider {
    private Keycloak keycloak;

    @Value("${jwt.admin-client.server-url}")
    private String serverUrl;

    @Value("${jwt.admin-client.realm}")
    private String realm;

    @Value("${jwt.admin-client.client-id}")
    private String clientId;

    @Value("${jwt.admin-client.username}")
    private String username;

    @Value("${jwt.admin-client.password}")
    private String password;

    @Value("${jwt.admin-client.secret-key}")
    private String secretKey;

    public Keycloak getKeycloakInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(secretKey)
                    .username(username)
                    .password(password)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();
        }
        System.out.println(this.serverUrl);
        return keycloak;
    }
}
