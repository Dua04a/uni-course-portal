package com.bdua.students.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

// class responsible for creating http
@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()
}

//[restTemplate] is for sending http for any external server