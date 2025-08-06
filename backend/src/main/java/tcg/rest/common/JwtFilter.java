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
            System.out.println("No Authorization header o no empieza por 'Bearer '");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            System.out.println("Token recibido: " + token);

            boolean valido = jwtGenerator.validateToken(token);
            System.out.println("¿Token válido?: " + valido);

            if (!valido) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String username = jwtGenerator.getUsernameFromToken(token);
            String role = jwtGenerator.getRoleFromToken(token);

            System.out.println("Usuario extraído: " + username);
            System.out.println("Rol extraído: " + role);

            request.setAttribute("serviceToken", token);
            configureSecurityContext(username, role);

        } catch (Exception e) {
            System.out.println("Error al procesar el token:");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void configureSecurityContext(String userName, String role) {
        if (role == null || role.isEmpty()) {
            System.out.println("⚠️ Rol vacío, no se configura el contexto de seguridad.");
            return; // No configuramos contexto si no hay rol
        }

        // Crea una authority con prefijo ROLE_
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        // Crea el token de autenticación
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userName, null, authorities);

        // Debug
        System.out.println("Usuario autenticado: " + userName);
        System.out.println("Rol: " + role);
        System.out.println("Authorities configuradas: " + authorities);

        // Establece el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


}
