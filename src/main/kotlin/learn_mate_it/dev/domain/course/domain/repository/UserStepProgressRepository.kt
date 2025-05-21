package learn_mate_it.dev.domain.course.domain.repository

import learn_mate_it.dev.domain.course.domain.model.UserStepProgress
import org.springframework.data.jpa.repository.JpaRepository

interface UserStepProgressRepository: JpaRepository<UserStepProgress, Long> {
}