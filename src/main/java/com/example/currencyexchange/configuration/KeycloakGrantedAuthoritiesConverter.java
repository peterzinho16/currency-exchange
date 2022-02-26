package com.example.currencyexchange.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private static final String GROUPS_CLAIM = "groups";
    private static final String ROLE_PREFIX = "ROLE_";


    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = grantedAuthorityCollection(jwt);
        CustomUserDetails customUserDetails = new CustomUserDetails(
                jwt.getClaim("email"), "", true, true,
                true, true, Collections.emptyList());
        customUserDetails.setRenwo(jwt.getClaim("sub"));
        var auth = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                "n/a",
                authorities);

        return Mono.just(
                new UsernamePasswordAuthenticationToken(
                        customUserDetails,
                        "n/a",
                        authorities)
        );
    }

    public Collection<GrantedAuthority> grantedAuthorityCollection(Jwt source) {
        Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
                .map(rn -> new SimpleGrantedAuthority(ROLE_PREFIX + rn.toUpperCase()))
                .collect(Collectors.toList());
    }
}
