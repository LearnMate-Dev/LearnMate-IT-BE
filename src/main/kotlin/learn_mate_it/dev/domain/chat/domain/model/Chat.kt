package learn_mate_it.dev.domain.chat.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "chats")
data class Chat(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var chatId: Long = 0,

    @Column(nullable = false)
    var author: Short,

    @Column(nullable = false, length = 500)
    var content: String,

    @Column(nullable = false, length = 300)
    var comment: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    var chatRoom: ChatRoom
) : BaseEntity()
