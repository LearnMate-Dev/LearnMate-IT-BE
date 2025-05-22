package learn_mate_it.dev.domain.chat.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDto
import learn_mate_it.dev.domain.chat.domain.enums.ChatRoomType
import learn_mate_it.dev.domain.chat.domain.model.ChatRoom
import learn_mate_it.dev.domain.chat.domain.repository.ChatRoomRepository
import learn_mate_it.dev.domain.user.domain.model.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(
    private val chatAiService: ChatAiService,
    private val chatRoomRepository: ChatRoomRepository
): ChatService {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun startTextChat(): ChatRoomDto {
        // TODO: get user info
        val user: User = User(
            username = "username"
        )

        // save chat room
        val chatRoom = ChatRoom(
            type = ChatRoomType.TEXT,
            userId = user.userId
        ).let { chatRoomRepository.save(it) }

        // get ai's recommend subject
        val recommendSubjects = chatAiService.getRecommendSubjects()

        return ChatRoomDto.toChatRoomDto(
            chatRoomId = chatRoom.chatRoomId,
            recommendSubjects = recommendSubjects
        )
    }

}