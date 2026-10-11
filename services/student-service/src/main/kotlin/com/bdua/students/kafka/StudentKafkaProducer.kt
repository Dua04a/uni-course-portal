package com.bdua.students.kafka

import com.bdua.students.Model.StudentUnenrolledEvent
import com.bdua.students.Model.StudentEnrolledEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper


//this class responsible to send the message "event" to kafka
@Service
class StudentKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    fun sendStudentUnenrolled(event: StudentUnenrolledEvent) {

        val json = objectMapper.writeValueAsString(event)

        kafkaTemplate.send(
            //topic
            "student-unenrolled",
            //key
            event.studentId.toString(),
            json
        )
    }

    fun sendStudentEnrolled(studentId: Int, courseId: Int) {
        val event = StudentEnrolledEvent(studentId, courseId)
        val json = objectMapper.writeValueAsString(event)

        kafkaTemplate.send(
            "student-enrolled",
            studentId.toString(),
            json
        )
    }
}
