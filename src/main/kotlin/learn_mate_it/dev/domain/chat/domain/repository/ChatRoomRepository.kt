package learn_mate_it.dev.domain.chat.domain.repository

import learn_mate_it.dev.domain.chat.domain.model.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository: JpaRepository<ChatRoom, Long> {

    fun findByChatRoomIdAndUserId(chatRoomId: Long, userId: Long): ChatRoom?

}