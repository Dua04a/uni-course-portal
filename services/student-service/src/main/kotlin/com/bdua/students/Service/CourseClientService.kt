package com.bdua.students.Service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CourseClientService(
    private val restTemplate: RestTemplate,
    @Value("\${course.service.url}") private val courseServiceUrl: String
) {
    private fun getCourseServiceToken(): String {
        return restTemplate.postForObject(
            "$courseServiceUrl/api/token",
            null,
            String::class.java
        ) ?: throw RuntimeException("Couldn't get token from course service")
    }

    fun getCourse(courseId: Int): Map<String, Any> {
        val token = getCourseServiceToken()
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")
        val entity = HttpEntity<Void>(headers)
        val response = restTemplate.exchange(
            "$courseServiceUrl/api/v1/courses/$courseId",
            HttpMethod.GET,
            entity,
            Map::class.java
        )
        return response.body as Map<String, Any>
    }
}
