package learn_mate_it.dev.domain.course.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import learn_mate_it.dev.domain.course.domain.enums.StepType
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "user_step_progress")
data class UserStepProgress(

    @Id
    @Column(nullable = false, columnDefinition = "UUID")
    var stepProgressId: UUID = UUID.randomUUID(),

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
