package com.example.scbook.configurations;

import com.example.scbook.filters.JwtTokenFilter;
import com.example.scbook.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer :: disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        //users---------------------------------------------------------------------------
                        .requestMatchers("**").permitAll()
                        .requestMatchers("/api/v1/users/register", "/api/v1/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/users**").permitAll()
                        //roles---------------------------------------------------------------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/roles**").permitAll()
                        //categories----------------------------------------------------------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories**")
                                .hasAnyRole(Role.USER, Role.ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/**")
                                .hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**")
                                .hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**")
                                .hasAnyRole(Role.ADMIN)
                        //products-------------------------------------------------------------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/top-sold-products")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/images/*").permitAll()
                        //orders---------------------------------------------------------------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders**").permitAll()
                                //.hasAnyRole(Role.USER, Role.ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/get-orders-by-keyword")
                        .       hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/**")
                                .hasAnyRole(Role.USER)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/**").hasRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasRole(Role.ADMIN)
                        //order_details-----------------------------------------------------------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/order_details**")
                        .hasAnyRole(Role.USER, Role.ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/order_details/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/order_details/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/order_details/**")
                        .hasAnyRole(Role.ADMIN)
                        .anyRequest().authenticated());
        http.cors(httpSecurityCorsConfigurer -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
            configuration.setExposedHeaders(List.of("x-auth-token"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            httpSecurityCorsConfigurer.configurationSource(source);
        });
        return http.build();
    }
}
