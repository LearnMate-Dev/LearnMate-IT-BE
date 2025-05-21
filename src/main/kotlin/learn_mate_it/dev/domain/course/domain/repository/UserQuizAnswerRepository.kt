package learn_mate_it.dev.domain.course.domain.repository

import learn_mate_it.dev.domain.course.domain.model.UserQuizAnswer
import learn_mate_it.dev.domain.course.domain.model.UserQuizAnswerId
import org.springframework.data.jpa.repository.JpaRepository

interface UserQuizAnswerRepository: JpaRepository<UserQuizAnswer, UserQuizAnswerId> {
}