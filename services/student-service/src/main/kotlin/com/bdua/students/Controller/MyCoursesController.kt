package com.bdua.students.Controller

import com.bdua.students.DTO.EnrolledCourseDto
import com.bdua.students.Service.StudentsCoursesService
import com.bdua.students.Service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/me/courses")
class MyCoursesController(
    private val userService: UserService,
    private val studentsCoursesService: StudentsCoursesService
) {
    @GetMapping
    fun getMyCourses(authentication: Authentication): List<EnrolledCourseDto> {
        val studentId = userService.getStudentId(authentication.name)
            ?: return emptyList()
        return studentsCoursesService.getCoursesForStudent(studentId).map {
            EnrolledCourseDto(
                id = it.id ?: 0,
                courseId = it.courseId,
                courseName = it.courseName,
                courseCode = it.courseCode,
                status = it.status
            )
        }
    }

    @PostMapping("/{courseId}")
    fun register(
        authentication: Authentication,
        @PathVariable courseId: Int
    ): ResponseEntity<String> {
        val studentId = userService.getStudentId(authentication.name)
            ?: return ResponseEntity.badRequest().body("Student profile not found")
        val result = studentsCoursesService.enrollStudent(studentId, courseId)
        return if (result == "Student enrolled successfully") {
            ResponseEntity.ok(result)
        } else {
            ResponseEntity.badRequest().body(result)
        }
    }
}
