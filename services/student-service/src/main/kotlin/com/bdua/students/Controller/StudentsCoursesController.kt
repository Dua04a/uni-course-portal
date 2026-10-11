package com.bdua.students.Controller

import com.bdua.students.Model.StudentsCourses
import com.bdua.students.DTO.CourseStudentDto
import com.bdua.students.Service.StudentsCoursesService
import org.springframework.web.bind.annotation.*

@RestController
class StudentsCoursesController(
    private val studentsCoursesService: StudentsCoursesService
) {
    @GetMapping("/api/v1/enrollment-counts")
    fun getEnrollmentCounts(): Map<Int, Int> =
        studentsCoursesService.getEnrollmentCountsByCourse()

    //get enrollment
    @GetMapping("/students-courses/{id}")
    fun getEnrollment(@PathVariable id: Int): StudentsCourses? {
        return studentsCoursesService.getEnrollment(id)
    }

    //enroll student in course
    @PostMapping("/students/{studentId}/courses/{courseId}")
    fun enrollStudent(
        @PathVariable studentId: Int,
        @PathVariable courseId: Int
    ): String {
        return studentsCoursesService.enrollStudent(studentId, courseId)
    }

    //unenroll student
    @PatchMapping("/students/{studentId}/unenroll")
    fun unenrollStudent(
        @PathVariable studentId: Int,
        @RequestBody request: UnenrollRequest
    ): String {
        return studentsCoursesService.unenrollStudent(
            studentId = studentId,
            reason = request.reason,
            courseId = request.courseId
        )
    }

    //test lazy loading
    @GetMapping("/students-courses/{id}/lazy")
    fun testLazy(@PathVariable id: Int): String {
        return studentsCoursesService.testLazy(id)
    }

    // get students registered in a course
    @GetMapping("/courses/{courseId}/students")
    fun getStudentsByCourse(@PathVariable courseId: Int): List<StudentsCourses> {
        return studentsCoursesService.getStudentsByCourse(courseId)
    }

    @GetMapping("/api/v1/courses/{courseId}/students")
    fun getStudentsByCourseForAdmin(@PathVariable courseId: Int): List<CourseStudentDto> {
        return studentsCoursesService.getStudentsByCourse(courseId).map {
            CourseStudentDto(it.id ?: 0, it.studentName)
        }
    }
}

data class UnenrollRequest(
    val reason: String,
    val courseId: Int? = null
)
