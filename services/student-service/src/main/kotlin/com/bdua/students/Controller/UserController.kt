package com.bdua.students.Controller

import com.bdua.students.Model.User
import com.bdua.students.DTO.AdminAccountDto
import com.bdua.students.DTO.CreateAccountRequest
import com.bdua.students.Service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    //get all users
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    //get secondary admin accounts without returning their passwords
    @GetMapping("/secondary-admins")
    fun getSecondaryAdmins(): List<AdminAccountDto> {
        return userService.getAllUsers()
            .filter { it.role == "ADMIN" && it.username != "AdminDua" }
            .map {
                AdminAccountDto(
                    id = it.id ?: 0,
                    username = it.username,
                    role = it.role
                )
            }
    }

    //get user by username
    @GetMapping("/{username}")
    fun getUserByUsername(
        @PathVariable username: String
    ): ResponseEntity<User> {
        val user = userService.getUserByUsername(username)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    //post - create user
    @PostMapping
    fun createUser(
        @RequestBody request: CreateAccountRequest
    ): ResponseEntity<User> {
        val createdUser = userService.createAccount(request)
        return ResponseEntity.ok(createdUser)
    }
}
