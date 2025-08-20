package learn_mate_it.dev.domain.chat.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.chat.domain.enums.ChatRoomType

@Entity
@Table(name = "chat_room")
data class ChatRoom(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var chatRoomId: Long = 0,

    @Column
    var title: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ChatRoomType,

    @Column(nullable = false)
    var userId: Long,

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var chats: MutableList<Chat> = mutableListOf(),

) : BaseEntity() {

    fun saveTitle(title: String) {
        this.title = title
    }

    fun validIsUserAuthorized(userId: Long) {
        if (this.userId != userId) {
            throw GeneralException(ErrorStatus.FORBIDDEN_FOR_CHAT_ROOM)
        }
    }

    fun ensureNotAnalysed() {
        if (this.title != null) {
            throw GeneralException(ErrorStatus.ALREADY_ANALYSIS_CHAT_ROOM)
        }
    }
}
