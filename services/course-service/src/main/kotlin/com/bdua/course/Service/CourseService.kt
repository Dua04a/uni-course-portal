package com.bdua.course.Service

import com.bdua.course.DTO.CourseRequestDto
import com.bdua.course.DTO.CourseResponseDto
import com.bdua.course.Mapper.CourseMapper
import com.bdua.course.Projection.CourseProjection
import com.bdua.course.Repository.CourseRepository
import com.bdua.course.Specification.CourseSpecification
import jakarta.transaction.Transactional
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.CacheEvict
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CourseService {

    @Autowired
    private lateinit var courseRepository: CourseRepository

    //bring all courses
    fun getAllCourses(): List<CourseResponseDto> {
        return courseRepository.findAll()
            .map { course ->
                CourseMapper.toResponseDto(course)
            }
    }

    //bring course by id
    //redis
    @Cacheable(
        value = ["courses"],
        key = "#id"
    )
    fun getCourseById(id: Int): CourseResponseDto {

        val course = courseRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Course with ID $id not found"
                )
            }

        return CourseMapper.toResponseDto(course)
    }

    //post
    fun addCourse(request: CourseRequestDto): CourseResponseDto {

        val course = CourseMapper.toEntity(request)

        val savedCourse = courseRepository.save(course)

        return CourseMapper.toResponseDto(savedCourse)
    }

    //patch
    @CacheEvict(value = ["courses"], key = "#id")
    @Transactional
    fun patchCourse(
        id: Int,
        request: CourseRequestDto
    ): CourseResponseDto {

        val course = courseRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Course with ID $id not found"
                )
            }

        CourseMapper.patchEntity(course, request)

        val updatedCourse = courseRepository.save(course)

        return CourseMapper.toResponseDto(updatedCourse)
    }

    //put
    @CacheEvict(value = ["courses"], key = "#id")
    @Transactional
    fun updateCourse(
        id: Int,
        request: CourseRequestDto
    ): CourseResponseDto {

        val course = courseRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Course with ID $id not found"
                )
            }

        CourseMapper.updateEntity(course, request)

        val updatedCourse = courseRepository.save(course)

        return CourseMapper.toResponseDto(updatedCourse)
    }

    //delete
    @CacheEvict(value = ["courses"], key = "#id")
    @Transactional
    fun deleteCourse(id: Int): String {

        return if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id)
            "Course with ID $id deleted successfully!"
        } else {
            "Course with ID $id not found"
        }
    }

    //projection
    fun getCoursesProjection(): List<CourseProjection> {
        return courseRepository.findAllProjectedBy()
    }

    //specification
    fun searchByCourseName(
        courseName: String
    ): List<CourseResponseDto> {

        val specification =
            CourseSpecification.hasCourseName(courseName)

        val courses = courseRepository.findAll(specification)

        if (courses.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Course not found"
            )
        }

        return courses.map { course ->
            CourseMapper.toResponseDto(course)
        }
    }

    //pagination n sorting
    fun getCourses(
        pageable: Pageable
    ): Page<CourseResponseDto> {

        return courseRepository.findAll(pageable)
            .map { course ->
                CourseMapper.toResponseDto(course)
            }
    }
}
