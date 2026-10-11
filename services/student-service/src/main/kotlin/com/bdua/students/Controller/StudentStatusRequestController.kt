package com.bdua.students.Controller

import com.bdua.students.DTO.CreateStudentStatusRequest
import com.bdua.students.DTO.StudentStatusRequestDto
import com.bdua.students.Service.StudentService
import com.bdua.students.Service.StudentStatusRequestService
import com.bdua.students.Service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class StudentStatusRequestController(
    private val userService: UserService,
    private val studentService: StudentService,
    private val requestService: StudentStatusRequestService
) {
    @GetMapping("/me/profile")
    fun getMyProfile(authentication: Authentication): ResponseEntity<Any> {
        val studentId = userService.getStudentId(authentication.name)
            ?: return ResponseEntity.badRequest().body<Any>("Student profile not found")
        return ResponseEntity.ok<Any>(studentService.getStudentById(studentId))
    }

    @GetMapping("/me/status-requests")
    fun getMyRequests(authentication: Authentication): ResponseEntity<Any> {
        val studentId = userService.getStudentId(authentication.name)
            ?: return ResponseEntity.badRequest().body<Any>("Student profile not found")
        return ResponseEntity.ok<Any>(requestService.getRequestsForStudent(studentId))
    }

    @PostMapping("/me/status-requests")
    fun createRequest(
        authentication: Authentication,
        @RequestBody request: CreateStudentStatusRequest
    ): ResponseEntity<Any> {
        val studentId = userService.getStudentId(authentication.name)
            ?: return ResponseEntity.badRequest().body<Any>("Student profile not found")
        return try {
            ResponseEntity.ok<Any>(requestService.submitRequest(studentId, request.requestedStatus))
        } catch (error: IllegalArgumentException) {
            ResponseEntity.badRequest().body<Any>(error.message ?: "Invalid request")
        }
    }

    @GetMapping("/admin/status-requests")
    fun getPendingRequests(): List<StudentStatusRequestDto> =
        requestService.getPendingRequests()

    @PatchMapping("/admin/status-requests/{id}/approve")
    fun approveRequest(@PathVariable id: Int): ResponseEntity<Any> = try {
        ResponseEntity.ok<Any>(requestService.approveRequest(id))
    } catch (error: IllegalArgumentException) {
        ResponseEntity.badRequest().body<Any>(error.message ?: "Could not approve request")
    }

    @PatchMapping("/admin/status-requests/{id}/reject")
    fun rejectRequest(@PathVariable id: Int): ResponseEntity<Any> = try {
        ResponseEntity.ok<Any>(requestService.rejectRequest(id))
    } catch (error: IllegalArgumentException) {
        ResponseEntity.badRequest().body<Any>(error.message ?: "Could not reject request")
    }
}
