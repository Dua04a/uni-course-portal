package com.bdua.course.Service

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class StudentClientService(
    private val restTemplate: RestTemplate,
    @Value("\${student.service.url}") private val studentServiceUrl: String
) {
    private fun getStudentServiceToken(): String {
        return restTemplate.postForObject(
            "$studentServiceUrl/api/token",
            null,
            String::class.java
        ) ?: throw RuntimeException("Couldn't get token from student service")
    }

    fun getAllStudents(): List<Map<String, Any>> {
        val token = getStudentServiceToken()
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")
        val entity = HttpEntity<Void>(headers)
        val response = restTemplate.exchange(
            "$studentServiceUrl/students",
            HttpMethod.GET,
            entity,
            object : ParameterizedTypeReference<List<Map<String, Any>>>() {}
        )
        return response.body ?: emptyList()
    }
}
