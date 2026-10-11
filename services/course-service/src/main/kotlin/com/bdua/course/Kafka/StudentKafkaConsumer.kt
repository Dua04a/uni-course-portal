package com.bdua.course.Kafka

import com.bdua.course.Model.StudentUnenrolledEvent
import com.bdua.course.Model.StudentEnrolledEvent
import com.bdua.course.Repository.CourseRepository
import org.springframework.cache.CacheManager
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class StudentKafkaConsumer(
    private val courseRepository: CourseRepository,
    private val objectMapper: ObjectMapper,
    private val cacheManager: CacheManager
) {

    @KafkaListener(topics = ["student-unenrolled"], groupId = "course-service")
    fun consume(message: String) {

        val event = objectMapper.readValue(message, StudentUnenrolledEvent::class.java)

        event.courseIds.forEach { courseId ->
            courseRepository.findById(courseId).ifPresent { course ->
                course.enrolledCount = (course.enrolledCount - 1).coerceAtLeast(0)
                courseRepository.save(course)
                cacheManager.getCache("courses")?.evict(courseId)
            }
        }
    }

    @KafkaListener(topics = ["student-enrolled"], groupId = "course-service")
    fun consumeEnrollment(message: String) {
        val event = objectMapper.readValue(message, StudentEnrolledEvent::class.java)
        courseRepository.findById(event.courseId).ifPresent { course ->
            course.enrolledCount += 1
            courseRepository.save(course)
            cacheManager.getCache("courses")?.evict(event.courseId)
        }
    }
}
