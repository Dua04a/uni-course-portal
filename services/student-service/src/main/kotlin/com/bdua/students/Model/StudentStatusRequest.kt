package com.bdua.students.Model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "student_status_requests")
class StudentStatusRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    var studentId: Int = 0
    var requestedStatus: String = ""
    var status: String = "PENDING"
}
