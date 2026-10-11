package com.bdua.course.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {

            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5174")
                    .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                    )
                    .allowedHeaders(
                        "Authorization",
                        "Content-Type",
                        "Accept",
                        "Range"
                    )
                    .exposedHeaders(
                        "Content-Range"
                    )
                    .allowCredentials(false)
            }
        }
    }
}