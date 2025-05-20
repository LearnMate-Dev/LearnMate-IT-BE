package learn_mate_it.dev.domain.course.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import learn_mate_it.dev.domain.course.domain.enums.CourseStatus
import learn_mate_it.dev.domain.course.domain.enums.CourseType

@Entity
@Table(name = "courses")
data class Course(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var courseId: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: CourseStatus,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: CourseType,

    @Column(nullable = false)
    var userId: Long,

    // TODO: resolve step entity

) : BaseEntity()