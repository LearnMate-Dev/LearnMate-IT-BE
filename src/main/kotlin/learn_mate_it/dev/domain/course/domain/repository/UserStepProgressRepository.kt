package learn_mate_it.dev.domain.course.domain.repository

import learn_mate_it.dev.domain.course.domain.enums.StepType
import learn_mate_it.dev.domain.course.domain.model.UserStepProgress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserStepProgressRepository: JpaRepository<UserStepProgress, Long> {

    fun findByStepProgressIdAndUserId(stepId: Long, userId: Long): UserStepProgress?
    fun existsByStepTypeAndUserIdAndCompletedAtIsNull(step: StepType, userId: Long): Boolean
    fun findByStepTypeInAndUserIdAndCompletedAtIsNotNull(previousStepList: List<StepType>, userId: Long): List<UserStepProgress>
    fun findByUserIdAndCompletedAtIsNotNull(userId: Long): List<UserStepProgress>

    @Modifying
    @Query("DELETE " +
                "FROM UserStepProgress us " +
                "WHERE us.userId IN :userId")
    fun deleteByUserId(@Param(value = "userId") userId: Long?)

}