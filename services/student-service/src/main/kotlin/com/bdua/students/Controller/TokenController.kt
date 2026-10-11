package com.bdua.students.Controller

import com.bdua.students.Security.JwtUtil
import com.bdua.students.Service.UserService
import com.bdua.students.dto.LoginRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:5173", "http://localhost:5174"])
@RestController
@RequestMapping("/api")
class TokenController {
    @Value("\${demo.student.username}")
    private lateinit var demoStudentUsername: String

    @Value("\${demo.student.password}")
    private lateinit var demoStudentPassword: String

    @Value("\${demo.admin.username}")
    private lateinit var demoAdminUsername: String

    @Value("\${demo.admin.password}")
    private lateinit var demoAdminPassword: String

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        val user = userService.getUserByUsername(request.username)
        if (user == null || user.password != request.password) {
            return ResponseEntity.status(401).body("Invalid username or password")
        }
        return ResponseEntity.ok(jwtUtil.generateToken(user.username, user.role))
    }

    @PostMapping("/token")
    fun generateStudentToken(@RequestBody request: LoginRequest): ResponseEntity<String> {
        if (request.username != demoStudentUsername || request.password != demoStudentPassword) {
            return ResponseEntity.status(401).body("Invalid username or password")
        }
        val token = jwtUtil.generateToken(demoStudentUsername, "STUDENT")
        return ResponseEntity.ok(token)
    }

    @PostMapping("/admin-token")
    fun generateAdminToken(@RequestBody request: LoginRequest): ResponseEntity<String> {
        if (request.username != demoAdminUsername || request.password != demoAdminPassword) {
            return ResponseEntity.status(401).body("Invalid username or password")
        }
        val token = jwtUtil.generateToken(demoAdminUsername, "ADMIN")
        return ResponseEntity.ok(token)
    }
}
