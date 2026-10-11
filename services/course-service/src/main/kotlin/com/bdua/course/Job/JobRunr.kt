package com.bdua.course.Job

import com.bdua.course.Repository.CourseRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

//spring scheduler
@Component
class JobRunr (
    private val courseRepository: CourseRepository )
{
    @Scheduled(cron ="0 * * * * *")
    fun runCourseJob() {
        val courses = courseRepository.findAll()
        courses.forEach { course ->
            println(
                "ID ${course.id}, " + "course: ${course.courseName}," + "Status: ${course.status}, " + "Enrolled: ${course.enrolledCount}"
            )
        }
    }
    }