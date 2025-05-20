package learn_mate_it.dev.domain.chat.domain.repository

import learn_mate_it.dev.domain.chat.domain.model.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository: JpaRepository<Chat, Long> {
}