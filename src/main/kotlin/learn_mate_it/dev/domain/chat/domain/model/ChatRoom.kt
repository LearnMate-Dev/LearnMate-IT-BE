package learn_mate_it.dev.domain.chat.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "chat_rooms")
data class ChatRoom(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var chatRoomId: Long = 0,

    @Column(nullable = false)
    var userId: Long,

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var chats: MutableList<Chat> = mutableListOf(),

    @OneToOne(mappedBy = "chatRoom", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var chatFeedback: ChatFeedback? = null
) : BaseEntity()
