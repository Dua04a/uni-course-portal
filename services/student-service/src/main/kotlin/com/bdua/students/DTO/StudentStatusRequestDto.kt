package com.bdua.students.DTO

data class StudentStatusRequestDto(
    val id: Int,
    val studentId: Int,
    val studentName: String,
    val requestedStatus: String,
    val status: String
)

data class CreateStudentStatusRequest(
    val requestedStatus: String
)
