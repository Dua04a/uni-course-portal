package com.bdua.students.Repository

import com.bdua.students.Model.StudentsCourses
import org.springframework.data.jpa.repository.JpaRepository

interface StudentsCoursesRepository : JpaRepository<StudentsCourses, Int> {

    fun findByCourseId(courseId: Int): List<StudentsCourses>
    fun existsByStudentIdAndCourseId(
        studentId: Int,
        courseId: Int
    ): Boolean

    fun findByStudentId(
        studentId: Int
    ): List<StudentsCourses>

    fun findByStudentIdAndCourseId(
        studentId: Int,
        courseId: Int
    ): StudentsCourses?
}