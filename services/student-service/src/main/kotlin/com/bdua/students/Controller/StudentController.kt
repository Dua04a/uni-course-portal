package com.bdua.students.Controller

import com.bdua.students.Model.Student
import com.bdua.students.Model.StudentsCourses
import com.bdua.students.Service.StudentService
import com.bdua.students.Service.StudentsCoursesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/students")
class StudentController(
    private val studentService: StudentService,
    private val studentsCoursesService: StudentsCoursesService
) {

    // get all students
    @GetMapping
    fun getAllStudents(): ResponseEntity<List<Student>> {

        val students = studentService.getAllStudents()

        val total = students.size

        val contentRange =
            if (total == 0) {
                "students 0-0/0"
            } else {
                "students 0-${students.lastIndex}/$total"
            }

        return ResponseEntity
            .ok()
            .header("Content-Range", contentRange)
            .body(students)
    }

    //get students registered in a course
    @GetMapping("/courses/{courseId}/students")
    fun getStudentsByCourse(
        @PathVariable courseId: Int
    ): List<StudentsCourses> {

        return studentsCoursesService.getStudentsByCourse(courseId)
    }

    //get student by ID
    @GetMapping("/{id}")
    fun getStudentById(
        @PathVariable id: Int
    ): Student {

        return studentService.getStudentById(id)
    }

    // post
    @PostMapping
    fun addStudent(
        @RequestBody student: Student
    ): Student {

        return studentService.addStudent(student)
    }

    //put
    @PutMapping("/{id}")
    fun updateStudent(
        @PathVariable id: Int,
        @RequestBody student: Student
    ): Student {

        return studentService.updateStudent(id, student)
    }

    //delete
    @DeleteMapping("/{id}")
    fun deleteStudent(
        @PathVariable id: Int
    ): String {

        return studentService.deleteStudent(id)
    }

    // patch
    @PatchMapping("/{id}")
    fun patchStudent(
        @PathVariable id: Int,
        @RequestBody student: Student
    ): Student {

        return studentService.patchStudent(id, student)
    }
}