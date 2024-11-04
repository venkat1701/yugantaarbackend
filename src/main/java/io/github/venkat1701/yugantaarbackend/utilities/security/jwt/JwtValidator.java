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
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

/**
 * JWT Validator filter that processes incoming requests to validate JWT tokens.
 * <p>
 * This filter extracts the JWT token from the request header, validates it,
 * and populates the Spring Security context with user authentication details.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
public class JwtValidator extends OncePerRequestFilter {

    /**
     * Validates the JWT token and sets the authentication in the SecurityContext.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing the request
     * @throws ServletException if an error occurs during the filter execution
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Remove "Bearer " prefix

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String email = String.valueOf(claims.get("email"));
                System.out.println(email);
                Long userId = claims.get("userId", Long.class);

                // Extract authorities from claims for RBAC implementation
                String authorities = String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Create authentication object with user details and roles
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT Token");
            }
        }

        // Proceed to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
