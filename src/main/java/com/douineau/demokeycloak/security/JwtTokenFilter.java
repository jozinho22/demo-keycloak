package com.douineau.demokeycloak.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtTokenFilter(JwtConfig jwtConfig) {
        super();
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Filter : JwtTokenVerifierFilter");
        System.out.println("Requête depuis un client : " + request.getMethod() + " - " +  request.getRequestURI());

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        if( (authorizationHeader == null || authorizationHeader.isEmpty())
                || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            System.out.println("No token available");
            filterChain.doFilter(request, response);
            return;
        }
        String token = null;

        try {
            token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

            @SuppressWarnings("deprecation")
            Jws<Claims> claimJws = Jwts
                    .parser()
                    .setSigningKey(jwtConfig.getSecretKeyForSigning())
                    .parseClaimsJws(token);
            Claims body = claimJws.getBody();
            String username = body.getSubject();

            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> grantedAuthorities =
                    authorities.stream()
                            .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                            .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken
                    (username,
                            null,
                            grantedAuthorities);

            if(authentication.isAuthenticated()) {
                System.out.println("Token OK pour le username : " + username);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch(JwtException e) {
            throw new IllegalStateException(String.format("can not be trusted", token));
        }

    }

}
