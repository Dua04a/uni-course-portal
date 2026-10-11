package com.bdua.students.DTO

data class CreateAccountRequest(
    val username: String,
    val password: String,
    val role: String,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val major: String = ""
)
