package learn_mate_it.dev.domain.course.domain.repository

import learn_mate_it.dev.domain.course.domain.enums.StepType
import learn_mate_it.dev.domain.course.domain.model.UserStepProgress
import org.springframework.data.jpa.repository.JpaRepository

interface UserStepProgressRepository: JpaRepository<UserStepProgress, Long> {

    fun userId(userId: Long): MutableList<UserStepProgress>
    fun findByStepProgressIdAndCompletedAtIsNull(stepId: Long): UserStepProgress?
    fun findByStepTypeAndUserIdAndCompletedAtIsNull(step: StepType, userId: Long): UserStepProgress?

}