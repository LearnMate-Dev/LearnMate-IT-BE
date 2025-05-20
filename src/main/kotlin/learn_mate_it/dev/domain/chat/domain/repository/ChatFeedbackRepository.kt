package learn_mate_it.dev.domain.chat.domain.repository

import learn_mate_it.dev.domain.chat.domain.model.ChatFeedback
import org.springframework.data.jpa.repository.JpaRepository

interface ChatFeedbackRepository: JpaRepository<ChatFeedback, Long> {
}