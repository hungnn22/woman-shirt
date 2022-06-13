package com.ws.masterserver.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.masterserver.utils.constants.WsConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/v1/login") ||
                request.getServletPath().equals("/api/v1/token/refresh") ||
                request.getServletPath().equals("/api/v1/auth/forgot-password/send-mail") ||
                request.getServletPath().equals("/api/v1/auth/reset-password") ||
                request.getServletPath().equals("/api/v1/auth/check-reset-token")) {
            filterChain.doFilter(request, response);
        } else {
            var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(WsConst.Values.BEARER_SPACE)) {
                try {
                    final var token = authorizationHeader.substring(WsConst.Values.BEARER_SPACE.length());
                    final var algorithm = Algorithm.HMAC256(WsConst.Values.JWT_SECRET.getBytes());
                    final var jwtVerifier = JWT.require(algorithm).build();
                    final var decodedJWT = jwtVerifier.verify(token);

                    final var id = decodedJWT.getClaim("id");
                    final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(decodedJWT.getClaim("role").asString()));

                    final var authenticationToken = new UsernamePasswordAuthenticationToken(
                            id, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    HashMap<String, Object> errors = new HashMap<>();
                    errors.put("statusCode", HttpStatus.FORBIDDEN.value());
                    errors.put("error", e.getMessage());
                    errors.put("timeStamp", new Date());
                    errors.put("path", request.getServletPath());
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

}
