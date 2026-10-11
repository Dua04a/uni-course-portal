package com.bdua.course.Controller

import com.bdua.course.DTO.CourseRequestDto
import com.bdua.course.DTO.CourseResponseDto
import com.bdua.course.Projection.CourseProjection
import com.bdua.course.Service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/courses")
class CourseController {

    @Autowired
    private lateinit var courseService: CourseService

    //bring all courses
    @GetMapping
    fun getAllCourses(): ResponseEntity<List<CourseResponseDto>> {

        val courses = courseService.getAllCourses()

        val total = courses.size

        val contentRange = if (total == 0) {
            "courses 0-0/0"
        } else {
            "courses 0-${total - 1}/$total"
        }

        return ResponseEntity
            .ok()
            .header("Content-Range", contentRange)
            .body(courses)
    }

    //pagination n sorting
    @GetMapping("/page")
    fun getCourses(
        pageable: Pageable
    ): Page<CourseResponseDto> {

        return courseService.getCourses(pageable)
    }

    //projection
    @GetMapping("/projection")
    fun getCoursesProjection(): List<CourseProjection> {

        return courseService.getCoursesProjection()
    }

    // specification / search
    @GetMapping("/search")
    fun searchCourses(
        @RequestParam courseName: String
    ): List<CourseResponseDto> {

        return courseService.searchByCourseName(courseName)
    }

    //bring course by ID
    @GetMapping("/{id}")
    fun getCourseById(
        @PathVariable id: Int
    ): CourseResponseDto {

        return courseService.getCourseById(id)
    }

    // post
    @PostMapping
    fun addCourse(
        @RequestBody request: CourseRequestDto
    ): CourseResponseDto {

        return courseService.addCourse(request)
    }

    //put
    @PutMapping("/{id}")
    fun updateCourse(
        @PathVariable id: Int,
        @RequestBody request: CourseRequestDto
    ): CourseResponseDto {

        return courseService.updateCourse(id, request)
    }

    // patch
    @PatchMapping("/{id}")
    fun patchCourse(
        @PathVariable id: Int,
        @RequestBody request: CourseRequestDto
    ): CourseResponseDto {

        return courseService.patchCourse(id, request)
    }

    // delete
    @DeleteMapping("/{id}")
    fun deleteCourse(
        @PathVariable id: Int
    ): String {

        return courseService.deleteCourse(id)
    }

    //course not found
    @ExceptionHandler(ResponseStatusException::class)
    fun handleException(
        exception: ResponseStatusException
    ): ResponseEntity<String> {

        return ResponseEntity
            .status(exception.statusCode)
            .body(exception.reason)
    }
}