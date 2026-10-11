package com.bdua.students.Model


//this is the message
data class StudentUnenrolledEvent(
    val studentId: Int,
    val courseIds: List<Int>,
    val reason: String
)

data class StudentEnrolledEvent(
    val studentId: Int,
    val courseId: Int
)
