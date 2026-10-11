package com.bdua.students.Service

import com.bdua.students.Model.User
import com.bdua.students.Model.Student
import com.bdua.students.DTO.CreateAccountRequest
import com.bdua.students.Repository.StudentRepository
import com.bdua.students.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    //get user by username
    fun getUserByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    //create new user
    fun createUser(user: User): User {
        user.id = null
        return userRepository.save(user)
    }

    fun createAccount(request: CreateAccountRequest): User {
        require(request.username.isNotBlank()) { "Username is required" }
        require(request.password.isNotBlank()) { "Password is required" }
        require(request.role == "ADMIN" || request.role == "STUDENT") {
            "Role must be ADMIN or STUDENT"
        }
        require(userRepository.findByUsername(request.username) == null) {
            "Username already exists"
        }

        val student = if (request.role == "STUDENT") {
            studentRepository.save(Student().apply {
                firstName = request.firstName
                lastName = request.lastName
                email = request.email
                major = request.major
            })
        } else null

        return userRepository.save(User().apply {
            username = request.username
            password = request.password
            role = request.role
            studentId = student?.id
        })
    }

    fun getStudentId(username: String): Int? =
        userRepository.findByUsername(username)?.studentId

    //get all users
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
}
