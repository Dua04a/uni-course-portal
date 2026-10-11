package com.bdua.students.Model

import jakarta.persistence.*

@Entity
@Table(name = "admins")
class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(unique = true, nullable = false)
    var username: String = ""

    @Column(nullable = false)
    var password: String = ""
}
