package learn_mate_it.dev.domain.course.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import learn_mate_it.dev.domain.course.domain.enums.StepType
import java.time.LocalDateTime

@Entity
@Table(name = "user_step_progress")
data class UserStepProgress(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var stepProgressId: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var stepType: StepType,

    @Column
    var completedAt: LocalDateTime? = null,

    @Column
    var durationTime: Long? = null,

    @Column(nullable = false)
    var userId: Long

) : BaseEntity()
