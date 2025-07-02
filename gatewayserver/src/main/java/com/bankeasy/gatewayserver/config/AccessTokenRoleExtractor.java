package com.bankeasy.gatewayserver.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Extracts roles from a JWT token issued by Keycloak and converts them into
 * Spring Security's {@link GrantedAuthority} objects for role-based access control.
 * This class looks for a "realm_access" claim containing a list of roles
 * and maps each role to a Spring Security role prefixed with "ROLE_".
 */
public class AccessTokenRoleExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {
    /**
     * Converts the roles found in the "realm_access" claim of the JWT token
     * into a collection of {@link GrantedAuthority} objects.
     *
     * @param jwt the JWT access token issued by the authorization server (e.g., Keycloak)
     * @return a collection of Spring Security authorities derived from token roles
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // getClaims retrieves the body / Map object from the jwt token
        // get the map object against the map key : realms
        Map<String, Object> realmDetails = jwt.getClaim("realm_access");
        if (realmDetails == null || realmDetails.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        //get Object value of roles key
        Object rolesObj =  realmDetails.get("roles");
        // check if list of items, if so assign to variable roles
        if (!(rolesObj instanceof List<?> roles)){
            return Collections.EMPTY_LIST;
        }
        //use stream to process roles instance
        return roles.stream()
        //filters out and returns only non-null items
                .filter(Objects::nonNull)
        //converts item to string
                .map(Object::toString)
        //perform prefixing to each role string item
                .map(roleName -> "ROLE_" + roleName)
        //for each role, create its respective spring authority grant object
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
