package tcg.rest.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtGenerator jwtGenerator;

    public JwtFilter(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);

            boolean valido = jwtGenerator.validateToken(token);

            if (!valido) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Long userId = jwtGenerator.getIdFromToken(token);
            String username = jwtGenerator.getUsernameFromToken(token);
            String role = jwtGenerator.getRoleFromToken(token);

            request.setAttribute("serviceToken", token);
            configureSecurityContext(userId, username, role);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void configureSecurityContext(Long userId, String userName, String role) {
        if (role == null || role.isEmpty()) {
            return;
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        Map<String, Object> userData = new HashMap<>();
            userData.put("id", userId);
            userData.put("userName", userName);

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userData, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


}
