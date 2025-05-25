package learn_mate_it.dev.domain.chat.domain.repository

import learn_mate_it.dev.domain.chat.domain.model.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ChatRoomRepository: JpaRepository<ChatRoom, Long> {

    fun findByChatRoomId(chatRoomId: Long): ChatRoom?

    @Query(value = "SELECT cr " +
            "FROM ChatRoom cr " +
            "WHERE cr.userId = :userId " +
            "AND cr.title IS NOT NULL " +
            "ORDER BY cr.createdAt DESC")
    fun findArchivedChatRoomList(@Param(value = "userId") userId: Long): List<ChatRoom>

    @Modifying
    @Query("DELETE " +
            "FROM ChatRoom cr " +
            "WHERE cr.chatRoomId IN :chatRoomId")
    fun deleteByChatRoomId(@Param(value = "chatRoomId") chatRoomId: Long)

}