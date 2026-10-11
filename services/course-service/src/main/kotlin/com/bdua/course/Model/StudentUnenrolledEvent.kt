package com.bdua.course.Model

data class StudentUnenrolledEvent(
    val studentId: Int,
    val courseIds: List<Int>,
    val reason: String
)

data class StudentEnrolledEvent(
    val studentId: Int,
    val courseId: Int
)
