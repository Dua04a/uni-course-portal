package com.bdua.course.Model

import com.yahoo.elide.annotation.Include
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Include(rootLevel = true, name = "courses")
@Entity
@Table(name = "course")
@SQLDelete(sql = "UPDATE course SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "courseName")
    var courseName: String? = ""

    @Column(name = "courseCode")
    var courseCode: String? = ""

    @Column(name = "instructorName")
    var instructorName: String? = ""

    @Column(name = "courseDescription")
    var courseDescription: String? = ""

    @Column(name = "status")
    var status: String = "OPEN"

    @Column(name = "enrolledCount")
    var enrolledCount: Int = 0

    @Column(name = "seatLimit")
    var seatLimit: Int? = 30

    @Column(name = "deleted_at")
var deletedAt: LocalDateTime? = null
}
