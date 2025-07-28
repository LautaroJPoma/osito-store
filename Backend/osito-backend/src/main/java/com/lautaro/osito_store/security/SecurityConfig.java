package com.lautaro.osito_store.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, 
                         AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
                @Override
                public void customize(CsrfConfigurer<HttpSecurity> csrf) {
                    csrf.disable();
                }
            })
            .authorizeHttpRequests(new Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
                @Override
                public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
                    try {
                        auth
                            // Endpoints públicos
                            .requestMatchers(
                                "/api/auth/**",
                                "/api/products",
                                "/api/products/{id}",
                                "/api/products/variants",
                                "/api/products/variants/{id}",
                                "/api/categories",
                                "/api/categories/{id}",
                                "/api/posts",
                                "/api/posts/{id}"
                            ).permitAll()
                            
                            // Categorías (solo admin puede modificar)
                            .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                            
                            // Posts (solo el dueño puede modificar)
                            .requestMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
                            
                            // Carrito y órdenes (solo usuarios autenticados)
                            .requestMatchers("/api/cart/**").hasRole("USER")
                            .requestMatchers("/api/cart-items/**").hasRole("USER")
                            .requestMatchers("/api/order-details/**").hasRole("USER")
                            .requestMatchers("/api/purchase-orders/**").hasRole("USER")
                            
                            // Usuarios
                            .requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()
                            .requestMatchers("/api/users/**").hasRole("ADMIN")
                            
                            // Cualquier otra petición requiere autenticación
                            .anyRequest().authenticated();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            })
            .sessionManagement(new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
                @Override
                public void customize(SessionManagementConfigurer<HttpSecurity> sess) {
                    sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }
            })
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}