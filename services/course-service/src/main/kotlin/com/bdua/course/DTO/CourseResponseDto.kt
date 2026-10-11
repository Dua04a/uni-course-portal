package com.bdua.course.DTO

data class CourseResponseDto(
    val id: Int,
    val courseName: String?,
    val courseCode: String?,
    val courseDescription: String?,
    val instructorName: String?,
    val seatLimit: Int,
    val enrolledCount: Int
)
