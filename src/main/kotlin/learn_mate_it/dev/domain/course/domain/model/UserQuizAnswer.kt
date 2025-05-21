package learn_mate_it.dev.domain.course.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import learn_mate_it.dev.domain.course.domain.enums.QuizType
import java.io.Serializable

data class UserQuizAnswerId(

    val stepProgressId: Long,
    val quizType: QuizType

) : Serializable

@Entity
@IdClass(UserQuizAnswerId::class)
@Table(name = "user_quiz_answer")
data class UserQuizAnswer(

    @Id
    @Column(nullable = false)
    val stepProgressId: Long,

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val quizType: QuizType,

    @Column(nullable = false)
    val selectedOptionIdx: Int,

    @Column(nullable = false)
    val isCorrect: Boolean

) : BaseEntity()
