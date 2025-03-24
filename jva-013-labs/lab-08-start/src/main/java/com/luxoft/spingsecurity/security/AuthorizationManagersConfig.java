package com.luxoft.spingsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
public class AuthorizationManagersConfig {

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManagerAny() {
        AuthorityAuthorizationManager<RequestAuthorizationContext> admin = AuthorityAuthorizationManager.hasRole("ADMIN");
        AuthorityAuthorizationManager<RequestAuthorizationContext> manager = AuthorityAuthorizationManager.hasRole("MANAGER");
        AuthorityAuthorizationManager<RequestAuthorizationContext> user = AuthorityAuthorizationManager.hasRole("USER");
        return AuthorizationManagers.anyOf(
                admin, manager, user
        );
    }
    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManagerAll() {
        AuthorityAuthorizationManager<RequestAuthorizationContext> admin = AuthorityAuthorizationManager.hasRole("ADMIN");
         return AuthorizationManagers.allOf(
                admin
        );
    }
}
