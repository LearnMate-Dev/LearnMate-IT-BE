package learn_mate_it.dev.domain.chat.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "chat_feedbacks")
data class ChatFeedback(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var chatFeedbackId: Long = 0,

    @Column(nullable = false, length = 500)
    var content: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    var chatRoom: ChatRoom? = null
) : BaseEntity()
