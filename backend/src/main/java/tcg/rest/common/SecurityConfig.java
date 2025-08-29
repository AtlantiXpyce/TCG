package tcg.rest.common;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtGenerator jwtGenerator;

    private String admin = "ADMIN";

    public SecurityConfig(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtFilter(jwtGenerator), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/cards/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/enableStore").authenticated()
                .requestMatchers(HttpMethod.POST, "/cardStock/add").authenticated()
                .requestMatchers(HttpMethod.GET, "/cardStock/languages").permitAll()
                .requestMatchers(HttpMethod.GET, "/cardStock/conditions").permitAll()
                .requestMatchers(HttpMethod.GET, "/cardStock/find").authenticated()
                .requestMatchers(HttpMethod.GET, "/cardStock/seller").authenticated()
                .requestMatchers(HttpMethod.POST, "/cardStock/delete").authenticated()
                .requestMatchers(HttpMethod.POST, "/cart").authenticated()
                .requestMatchers(HttpMethod.GET, "/cart").authenticated()
                .requestMatchers(HttpMethod.POST, "/cart/delete").authenticated()
                .requestMatchers(HttpMethod.POST, "/cart/buy").authenticated()
                .requestMatchers(HttpMethod.GET, "/orders").authenticated()
                .requestMatchers(HttpMethod.GET, "/orders/seller").authenticated()
                .requestMatchers(HttpMethod.GET, "/orders/lines").authenticated()
                .requestMatchers(HttpMethod.POST, "/orders/review").authenticated()
                .requestMatchers(HttpMethod.POST, "/admin/deleteCard").hasRole(admin)
                .requestMatchers(HttpMethod.POST, "/admin/deleteStock").hasRole(admin)
                .requestMatchers(HttpMethod.POST, "/admin/deleteUser").hasRole(admin)
                .requestMatchers(HttpMethod.GET, "/admin/getUsers").hasRole(admin)
                .anyRequest().denyAll()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
