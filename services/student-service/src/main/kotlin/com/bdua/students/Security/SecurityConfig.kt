package com.bdua.students.Security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/token", "/api/admin-token", "/api/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole("ADMIN")
                    .requestMatchers("/api/v1/students/**").hasRole("ADMIN")
                    .requestMatchers("/api/v1/admins/**").hasRole("ADMIN")
                    .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                    .requestMatchers("/api/v1/me/**").hasRole("STUDENT")
                    .requestMatchers(
                        "/api/v1/admin/status-requests",
                        "/api/v1/admin/status-requests/**"
                    ).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/enrollment-counts")
                    .hasAnyRole("ADMIN", "STUDENT")
                    .requestMatchers(HttpMethod.GET, "/api/v1/courses/*/students").hasRole("ADMIN")
                    .requestMatchers("/courses/*/students", "/students/**", "/students-courses/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:5173", "http://localhost:5174")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type", "Accept", "Range")
        configuration.exposedHeaders = listOf("Content-Range")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
