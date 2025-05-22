package learn_mate_it.dev.domain.chat.domain.repository

import learn_mate_it.dev.domain.chat.domain.model.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ChatRepository: JpaRepository<Chat, Long> {

    @Modifying
    @Query("DELETE " +
            "FROM Chat c " +
            "WHERE c.chatRoom.chatRoomId IN :chatRoomId")
    fun deleteByChatRoomId(@Param(value = "chatRoomId") chatRoomId: Long?)

}