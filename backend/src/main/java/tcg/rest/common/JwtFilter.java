package tcg.rest.common;

import java.io.IOException;
import java.util.List;

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

            String username = jwtGenerator.getUsernameFromToken(token);
            String role = jwtGenerator.getRoleFromToken(token);

            request.setAttribute("serviceToken", token);
            configureSecurityContext(username, role);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void configureSecurityContext(String userName, String role) {
        if (role == null || role.isEmpty()) {
            return; // No configuramos contexto si no hay rol
        }

        // Crea una authority con prefijo ROLE_
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        // Crea el token de autenticación
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userName, null, authorities);

        // Establece el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


}
