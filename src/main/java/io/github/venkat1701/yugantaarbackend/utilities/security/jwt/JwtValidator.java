package io.github.venkat1701.yugantaarbackend.utilities.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.github.venkat1701.yugantaarbackend.utilities.authorities.YugantaarGrantedAuthority;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String email = String.valueOf(claims.get("email"));
                Long userId = claims.get("userId", Long.class);

                // Create proper YugantaarGrantedAuthority objects
                List<GrantedAuthority> authorities = new ArrayList<>();

                // Add roles
                List<String> roles = claims.get("roles", List.class);
                if (roles != null) {
                    for (String role : roles) {
                        authorities.add(new YugantaarGrantedAuthority("ROLE_" + role, "ROLE"));
                    }
                }

                // Add permissions
                List<String> permissions = claims.get("permissions", List.class);
                if (permissions != null) {
                    for (String permission : permissions) {
                        authorities.add(new YugantaarGrantedAuthority(permission, "PERMISSION"));
                    }
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT Token");
            }
        }

        filterChain.doFilter(request, response);
    }
}