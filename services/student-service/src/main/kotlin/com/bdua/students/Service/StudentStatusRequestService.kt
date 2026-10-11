package com.bdua.students.Service

import com.bdua.students.DTO.StudentStatusRequestDto
import com.bdua.students.Model.StudentStatusRequest
import com.bdua.students.Repository.StudentRepository
import com.bdua.students.Repository.StudentStatusRequestRepository
import org.springframework.stereotype.Service

@Service
class StudentStatusRequestService(
    private val requestRepository: StudentStatusRequestRepository,
    private val studentRepository: StudentRepository,
    private val studentsCoursesService: StudentsCoursesService
) {
    private val allowedStatuses = setOf(
        "WITHDRAWN_FROM_UNIVERSITY",
        "TERM_DEFERRED",
        "GRADUATED"
    )

    fun getPendingRequests(): List<StudentStatusRequestDto> =
        requestRepository.findByStatusOrderByIdDesc("PENDING").map(::toDto)

    fun getRequestsForStudent(studentId: Int): List<StudentStatusRequestDto> =
        requestRepository.findByStudentIdOrderByIdDesc(studentId).map(::toDto)

    fun submitRequest(studentId: Int, requestedStatus: String): StudentStatusRequestDto {
        require(requestedStatus in allowedStatuses) { "Invalid requested status" }
        val student = studentRepository.findById(studentId).orElse(null)
            ?: throw IllegalArgumentException("Student profile not found")
        require(student.status == "ACTIVE") { "Only active students can request a status change" }
        require(!requestRepository.existsByStudentIdAndStatus(studentId, "PENDING")) {
            "You already have a pending request"
        }

        val request = StudentStatusRequest().apply {
            this.studentId = studentId
            this.requestedStatus = requestedStatus
        }
        return toDto(requestRepository.save(request))
    }

    fun approveRequest(id: Int): StudentStatusRequestDto {
        val request = requestRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Request not found")
        require(request.status == "PENDING") { "This request was already reviewed" }

        val result = studentsCoursesService.unenrollStudent(
            studentId = request.studentId,
            reason = request.requestedStatus
        )
        require(result == "Student status updated" || result == "Student unenrollment processed successfully") {
            result
        }

        request.status = "APPROVED"
        return toDto(requestRepository.save(request))
    }

    fun rejectRequest(id: Int): StudentStatusRequestDto {
        val request = requestRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Request not found")
        require(request.status == "PENDING") { "This request was already reviewed" }
        request.status = "REJECTED"
        return toDto(requestRepository.save(request))
    }

    private fun toDto(request: StudentStatusRequest): StudentStatusRequestDto {
        val student = studentRepository.findById(request.studentId).orElse(null)
        val name = listOfNotNull(student?.firstName, student?.lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")

        return StudentStatusRequestDto(
            id = request.id ?: 0,
            studentId = request.studentId,
            studentName = name,
            requestedStatus = request.requestedStatus,
            status = request.status
        )
    }
}
