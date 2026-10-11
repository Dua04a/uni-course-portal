package com.bdua.students.DTO

data class EnrolledCourseDto(
    val id: Int,
    val courseId: Int,
    val courseName: String,
    val courseCode: String,
    val status: String
)
