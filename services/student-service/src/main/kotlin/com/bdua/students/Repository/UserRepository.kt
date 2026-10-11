package com.bdua.students.Repository

import com.bdua.students.Model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {

    fun findByUsername(username: String): User?
}