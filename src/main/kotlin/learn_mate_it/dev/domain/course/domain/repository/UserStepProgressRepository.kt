package learn_mate_it.dev.domain.course.domain.repository

import learn_mate_it.dev.domain.course.domain.enums.StepType
import learn_mate_it.dev.domain.course.domain.model.UserStepProgress
import org.springframework.data.jpa.repository.JpaRepository

interface UserStepProgressRepository: JpaRepository<UserStepProgress, Long> {

    fun findByStepProgressIdAndUserId(stepId: Long, userId: Long): UserStepProgress?
    fun existsByStepTypeAndUserIdAndCompletedAtIsNull(step: StepType, userId: Long): Boolean
    fun findByStepTypeInAndUserIdAndCompletedAtIsNotNull(previousStepList: List<StepType>, userId: Long): List<UserStepProgress>
    fun findByUserIdAndCompletedAtIsNotNull(userId: Long): List<UserStepProgress>

}