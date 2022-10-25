package com.copterbuddy.prototype.backend.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.copterbuddy.prototype.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public TokenFilter(TokenService service) {
        this.tokenService = service;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = request.getRequestURI();
        if (request.getRequestURI().startsWith("/socket/info")
                || request.getRequestURI().startsWith("/socket/info/")) {
            return true;
        }


        if (request.getRequestURI().startsWith("/socket")
                || request.getRequestURI().endsWith("/websocket")) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(authorization)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!authorization.startsWith("Bearer ")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = authorization.substring(7);
        DecodedJWT decoded = tokenService.verify(token);
        if (decoded == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // user id
        String principal = decoded.getClaim("principal").asString();
        String role = decoded.getClaim("role").asString();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,"(protected)",authorities);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
