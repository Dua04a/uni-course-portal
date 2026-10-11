package com.bdua.course.Controller

import com.bdua.course.Security.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:5174"])
@RestController
@RequestMapping("/api")
class TokenController {
    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @PostMapping("/token")
    fun generateToken(): String = jwtUtil.generateToken("student-user", "STUDENT")

}
