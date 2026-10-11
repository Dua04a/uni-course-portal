package com.bdua.students.Repository

import com.bdua.students.Model.StudentStatusRequest
import org.springframework.data.jpa.repository.JpaRepository

interface StudentStatusRequestRepository : JpaRepository<StudentStatusRequest, Int> {
    fun findByStatusOrderByIdDesc(status: String): List<StudentStatusRequest>
    fun findByStudentIdOrderByIdDesc(studentId: Int): List<StudentStatusRequest>
    fun existsByStudentIdAndStatus(studentId: Int, status: String): Boolean
}
