package com.bdua.course.Mapper

import com.bdua.course.DTO.CourseRequestDto
import com.bdua.course.DTO.CourseResponseDto
import com.bdua.course.Model.Course

object CourseMapper {

    //request dto -> entity
    fun toEntity(request: CourseRequestDto): Course {

        val course = Course()

        course.courseName = request.courseName
        course.courseCode = request.courseCode
        course.courseDescription = request.courseDescription
        course.instructorName = request.instructorName
        course.seatLimit = request.seatLimit ?: 30

        return course
    }


    // entity -> response dto
    fun toResponseDto(course: Course): CourseResponseDto {

        return CourseResponseDto(
            id = course.id!!,
            courseName = course.courseName,
            courseCode = course.courseCode,
            courseDescription = course.courseDescription,
            instructorName = course.instructorName,
            seatLimit = course.seatLimit ?: 30,
            enrolledCount = course.enrolledCount
        )
    }


    //put
    // Update all fields
    fun updateEntity(
        course: Course,
        request: CourseRequestDto
    ) {

        course.courseName = request.courseName
        course.courseCode = request.courseCode
        course.courseDescription = request.courseDescription
        course.instructorName = request.instructorName
        course.seatLimit = request.seatLimit ?: course.seatLimit ?: 30
    }


    //patvh
    // update specific fields
    fun patchEntity(
        course: Course,
        request: CourseRequestDto
    ) {

        if (!request.courseName.isNullOrEmpty()) {
            course.courseName = request.courseName
        }

        if (!request.courseCode.isNullOrEmpty()) {
            course.courseCode = request.courseCode
        }

        if (!request.courseDescription.isNullOrEmpty()) {
            course.courseDescription = request.courseDescription
        }

        if (!request.instructorName.isNullOrEmpty()) {
            course.instructorName = request.instructorName
        }
        if (request.seatLimit != null) {
            course.seatLimit = request.seatLimit
        }
    }
}
