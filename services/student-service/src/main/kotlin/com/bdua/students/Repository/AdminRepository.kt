package com.bdua.students.Repository

import com.bdua.students.Model.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Int> {
    fun findByUsername(username: String): Admin?
}
