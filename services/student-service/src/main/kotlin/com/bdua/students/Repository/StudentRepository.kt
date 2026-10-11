package com.bdua.students.Repository

import com.bdua.students.Model.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Int>