package com.bdua.course.Repository

import com.bdua.course.Model.Course
import com.bdua.course.Projection.CourseProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor


interface CourseRepository : JpaRepository<Course, Int>, JpaSpecificationExecutor<Course> {    //projection
    fun findAllProjectedBy(): List<CourseProjection>
}
