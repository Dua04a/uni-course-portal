package com.bdua.course.Controller

import com.bdua.course.Service.StudentClientService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CourseStudentController(
    private val studentClientService: StudentClientService
) {
    @GetMapping("/courses/students")
    fun getStudentsFromStudentService(): List<Map<String, Any>> =
        studentClientService.getAllStudents()
}
