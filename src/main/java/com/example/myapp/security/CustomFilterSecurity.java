package com.example.myapp.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.myapp.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFilterSecurity extends OncePerRequestFilter  {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public CustomFilterSecurity(final JwtService jwtService, final UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (request.getRequestURI().matches("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if ((authorization == null || !authorization.startsWith("Bearer "))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "La autenticación está vacía o presenta un mal formato.");
            return;
        }

        String token = authorization.split(" ")[1];

        try {
            String email = (String) jwtService.getClaim(token, "email");

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            System.out.println(authentication);

            filterChain.doFilter(request, response);
            
        }
        catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "asd");
        }
        
        
    }
    
}
