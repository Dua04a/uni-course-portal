package com.bdua.course.Specification

import com.bdua.course.Model.Course
import org.springframework.data.jpa.domain.Specification

object CourseSpecification {

    ///hascoursename it can receive specific word
    fun hasCourseName(courseName: String, ): Specification<Course> {
      //by spring
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.like(
                root.get("courseName"),
                "%$courseName%"
            )
        }
    }
}