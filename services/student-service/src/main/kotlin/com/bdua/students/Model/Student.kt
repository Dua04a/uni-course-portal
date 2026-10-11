package com.bdua.students.Model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "students")
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "firstName")
    var firstName: String = ""

    @Column(name = "lastName")
    var lastName: String = ""

    @Column(name = "email")
    var email: String = ""

    @Column(name = "major")
    var major: String = ""

    @Column(name = "status")
    var status: String = "ACTIVE"

    @Column(name = "role")
    var role: String = "STUDENT"
}
