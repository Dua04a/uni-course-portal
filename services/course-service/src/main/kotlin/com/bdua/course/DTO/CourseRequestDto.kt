package com.bdua.course.DTO

class CourseRequestDto (
    val courseName: String?,
    val courseCode: String?,
    val instructorName: String?,
    val courseDescription: String?,
    val seatLimit: Int? = 30,
)
