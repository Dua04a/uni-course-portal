package com.bdua.course.Job

import com.bdua.course.Repository.CourseRepository
import org.jobrunr.jobs.annotations.Job
import org.springframework.stereotype.Component

@Component
class CourseJob(
    private val courseRepository: CourseRepository
) {
    @Job(name = "runCourseJob")
    fun printCourses() {
        val courses = courseRepository.findAll()

        courses.forEach { course ->
            println(
                "ID: ${course.id}, " + "Course: ${course.courseName}, " + "Status: ${course.status}, " +"Enrolled: ${course.enrolledCount}"
            )
        }
    }
}