package com.bdua.students.Service

import com.bdua.students.Model.StudentUnenrolledEvent
import com.bdua.students.Model.StudentsCourses
import com.bdua.students.Repository.StudentRepository
import com.bdua.students.Repository.StudentsCoursesRepository
import com.bdua.students.kafka.StudentKafkaProducer
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudentsCoursesService {

    @Autowired
    private lateinit var studentsCoursesRepository: StudentsCoursesRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var courseClientService: CourseClientService

    @Autowired
    private lateinit var studentKafkaProducer: StudentKafkaProducer

    // get students registered in a course
    fun getStudentsByCourse(courseId: Int): List<StudentsCourses> {
        return studentsCoursesRepository.findByCourseId(courseId)
            .filter { it.status == "ENROLLED" }
    }

    fun getCoursesForStudent(studentId: Int): List<StudentsCourses> {
        return studentsCoursesRepository.findByStudentId(studentId)
            .filter { it.status == "ENROLLED" }
    }

    fun getEnrollmentCountsByCourse(): Map<Int, Int> {
        return studentsCoursesRepository.findAll()
            .filter { it.status == "ENROLLED" }
            .groupingBy { it.courseId }
            .eachCount()
    }

    //enroll student in course
    fun enrollStudent(studentId: Int, courseId: Int): String {

        val student = studentRepository.findById(studentId).orElse(null)

        if (student == null) {
            return "Student with ID $studentId not found"
        }

        val course = try {
            courseClientService.getCourse(courseId)
        } catch (e: Exception) {
            e.printStackTrace()
            return "ERROR: ${e.message}"
        }

        val courseCode = course["courseCode"]?.toString() ?: ""
        val courseName = course["courseName"]?.toString() ?: ""
        val seatLimit = (course["seatLimit"] as? Number)?.toInt() ?: 30

        val existingEnrollment =
            studentsCoursesRepository.findByStudentIdAndCourseId(
                studentId,
                courseId
            )

        //already enrolled
        if (existingEnrollment != null &&
            existingEnrollment.status == "ENROLLED"
        ) {
            return "Student is already enrolled in this course"
        }

        val enrolledCount = studentsCoursesRepository.findByCourseId(courseId)
            .count { it.status == "ENROLLED" }
        if (enrolledCount >= seatLimit) {
            return "Course is full"
        }

        if (existingEnrollment != null) {

            existingEnrollment.status = "ENROLLED"
            existingEnrollment.courseCode = courseCode
            existingEnrollment.courseName = courseName

            studentsCoursesRepository.save(existingEnrollment)
            studentKafkaProducer.sendStudentEnrolled(studentId, courseId)

            return "Student enrolled successfully"
        }

        //create new enrollment
        val enrollment = StudentsCourses(
            studentId = studentId,
            studentName = "${student.firstName} ${student.lastName}",
            courseId = courseId,
            courseCode = courseCode,
            courseName = courseName
        )

        studentsCoursesRepository.save(enrollment)
        studentKafkaProducer.sendStudentEnrolled(studentId, courseId)

        return "Student enrolled successfully"
    }


    fun unenrollStudent(
        studentId: Int,
        reason: String,
        courseId: Int? = null
    ): String {

        val student = studentRepository.findById(studentId).orElse(null)
            ?: return "Student with ID $studentId not found"


        //the reason first
        val newStatus = when (reason) {

            "COURSE_UNENROLLED" -> "UNENROLLED"

            "WITHDRAWN_FROM_UNIVERSITY" -> "WITHDRAWN"

            "TERM_DEFERRED" -> "DEFERRED"

            "GRADUATED" -> "GRADUATED"

            else -> return "Invalid reason"
        }

        val enrollments = if (courseId != null) {

            val enrollment =
                studentsCoursesRepository.findByStudentIdAndCourseId(
                    studentId,
                    courseId
                )

            if (enrollment == null) {
                emptyList()
            } else {
                listOf(enrollment)
            }

        } else {

            studentsCoursesRepository
                .findByStudentId(studentId)
                .filter { it.status == "ENROLLED" }
        }


        if (courseId == null) {
            student.status = reason
            studentRepository.save(student)
        }

        if (enrollments.isEmpty()) {
            return if (courseId == null) {
                "Student status updated"
            } else {
                "Student has no enrolled courses"
            }
        }


        //update enrollment status
        enrollments.forEach { enrollment ->
            enrollment.status = newStatus
        }

        studentsCoursesRepository.saveAll(enrollments)


        val courseIds = enrollments.map { it.courseId }

        //create event
        val event = StudentUnenrolledEvent(
            studentId = studentId,
            courseIds = courseIds,
            reason = reason
        )

        //send event to Kafka
        studentKafkaProducer.sendStudentUnenrolled(event)


        return "Student unenrollment processed successfully"
    }

    fun getEnrollment(id: Int): StudentsCourses? =
        studentsCoursesRepository.findById(id).orElse(null)

    //lazy n eager loading
    @Transactional
    fun testLazy(id: Int): String {
        val enrollment = studentsCoursesRepository.findById(id).orElse(null)
            ?: return "Enrollment not found"
        val before = Hibernate.isInitialized(enrollment.student)
        return "Student loaded before access: $before"
    }
}
