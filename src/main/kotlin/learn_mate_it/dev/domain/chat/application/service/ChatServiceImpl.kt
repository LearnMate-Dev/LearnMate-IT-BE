package learn_mate_it.dev.domain.chat.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.chat.application.dto.request.ChatRequest
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDto
import learn_mate_it.dev.domain.chat.domain.enums.ChatAuthor
import learn_mate_it.dev.domain.chat.domain.enums.ChatRoomType
import learn_mate_it.dev.domain.chat.domain.model.Chat
import learn_mate_it.dev.domain.chat.domain.model.ChatRoom
import learn_mate_it.dev.domain.chat.domain.repository.ChatRepository
import learn_mate_it.dev.domain.chat.domain.repository.ChatRoomRepository
import learn_mate_it.dev.domain.user.domain.model.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(
    private val chatAiService: ChatAiService,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRepository: ChatRepository
): ChatService {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Start chat with Text And Get recommend chat subject from AI
     *
     * @return ChatRoomDto chatRoomId and recommend subject list
     */
    @Transactional
    override fun startTextChat(): ChatRoomDto {
        val user: User = getUser()

        // save chat room
        val chatRoom = chatRoomRepository.save(
            ChatRoom(
                type = ChatRoomType.TEXT,
                userId = user.userId
            )
        )

        // get ai's recommend subject
        val recommendSubjects = chatAiService.getRecommendSubjects()

        return ChatRoomDto.toChatRoomDto(
            chatRoomId = chatRoom.chatRoomId,
            recommendSubjects = recommendSubjects
        )
    }

    /**
     * Chat with Text
     *
     * @param chatRoomId id of chat room
     * @param request content of chat
     * @return ChatDto id and content of Ai response
     */
    @Transactional
    override fun chatWithText(chatRoomId: Long, request: ChatRequest): ChatDto {
        val user: User = getUser()

        // get chat room and save chat
        val chatRoom: ChatRoom = getChatRoom(chatRoomId, user.userId)
        validChatContentLength(request.content, false)
        chatRepository.save(
            Chat(
                author = ChatAuthor.HUMAN,
                content = request.content,
                chatRoom = chatRoom
            )
        )

        // get ai's response
        val response = chatAiService.getChatResponse(request.content)
        validChatContentLength(response, true)
        val aiChat = chatRepository.save(
            Chat(
                author = ChatAuthor.AI,
                content = response,
                chatRoom = chatRoom
            )
        )

        return ChatDto.toChatDto(aiChat)
    }

    private fun validChatContentLength(content: String, isChatResponse: Boolean) {
        if (content.length <= 500) return

        val errorStatus = if (isChatResponse) {
            ErrorStatus.CHAT_AI_CONTENT_OVER_FLOW
        } else {
            ErrorStatus.CHAT_CONTENT_OVER_FLOW
        }
        throw GeneralException(errorStatus)
    }

    private fun getChatRoom(chatRoomId: Long, userId: Long): ChatRoom {
        return chatRoomRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_CHAT_ROOM)
    }

    private fun getUser(): User {
        // TODO: get user info
        return User(username = "username")
    }

}