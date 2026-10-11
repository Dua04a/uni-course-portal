package com.bdua.students.Service

import com.bdua.students.Model.Student
import com.bdua.students.Repository.StudentRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentService {

    @Autowired
    private lateinit var studentRepository: StudentRepository

    // get
    fun getAllStudents(): List<Student> =
        studentRepository.findAll()

    // get student by id
    fun getStudentById(id: Int): Student {
        return studentRepository.findById(id).orElseThrow()
    }

    // post
    fun addStudent(student: Student): Student {
        student.id = null
        return studentRepository.save(student)
    }

    // put
    fun updateStudent(id: Int, student: Student): Student {
        student.id = id
        return studentRepository.save(student)
    }

    // patch
    @Transactional
    fun patchStudent(id: Int, student: Student): Student {
        val existingStudent = studentRepository.findById(id).orElseThrow()

        if (student.firstName.isNotEmpty()) {
            existingStudent.firstName = student.firstName
        }

        if (student.lastName.isNotEmpty()) {
            existingStudent.lastName = student.lastName
        }

        if (student.email.isNotEmpty()) {
            existingStudent.email = student.email
        }

        if (student.major.isNotEmpty()) {
            existingStudent.major = student.major
        }

        if (student.role.isNotEmpty()) {
            existingStudent.role = student.role
        }

        return studentRepository.save(existingStudent)
    }

    // delete
    @Transactional
    fun deleteStudent(id: Int): String {
        return if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id)
            "Student with ID $id deleted successfully"
        } else {
            "Student with ID $id not found"
        }

    }
}