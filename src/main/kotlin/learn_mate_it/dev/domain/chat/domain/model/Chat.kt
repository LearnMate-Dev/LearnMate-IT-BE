package learn_mate_it.dev.domain.chat.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import learn_mate_it.dev.domain.chat.domain.enums.ChatAuthor

@Entity
@Table(name = "chat")
data class Chat(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var chatId: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var author: ChatAuthor,

    @Column(nullable = false, length = 500)
    var content: String,

    @Column(length = 300)
    var comment: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    var chatRoom: ChatRoom

) : BaseEntity() {

    fun updateComment(comment: String) {
        this.comment = comment
    }
}
