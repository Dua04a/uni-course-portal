package com.bdua.course.Job

import org.jobrunr.jobs.annotations.Job
import org.jobrunr.scheduling.JobScheduler
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class CourseJobScheduler (
    private val jobScheduler : JobScheduler,
            private val courseJob : CourseJob
)  : CommandLineRunner {

    override fun run(vararg args: String) {
      //schedule (just for ones)
        jobScheduler.schedule(
            LocalDateTime.of(2026, 9, 13, 15, 4)
        ) {
            courseJob.printCourses()
        }
//schedule (every time)
        //jobScheduler.enqueue {
           // courseJob.printCourses()
        }
    }
