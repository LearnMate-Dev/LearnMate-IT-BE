package learn_mate_it.dev.domain.course.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "steps")
data class Step(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var stepId: Long = 0,

    // TODO:
) : BaseEntity()
