package com.bdua.students.Model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "students_courses")
class StudentsCourses(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var studentId: Int,

    var studentName: String,
    var courseId: Int,
    var courseCode: String,
    var courseName: String,
    var status: String = "ENROLLED",

    //lazy n eager loading
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "studentId",
        insertable = false,
        updatable = false
    )
    var student: Student? = null
)
