package learn_mate_it.dev.domain.chat.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.chat.application.dto.request.ChatRequest
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDetailDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomInitDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomListDto
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

    private final val CONTENT_LENGTH: Int = 500
    private final val TITLE_LENGTH: Int = 30
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Start chat with Text And Get recommend chat subject from AI
     *
     * @return ChatRoomDto chatRoomId and recommend subject list
     */
    @Transactional
    override fun startTextChat(): ChatRoomInitDto {
        val user = getUser()

        // save chat room
        val chatRoom = chatRoomRepository.save(
            ChatRoom(
                type = ChatRoomType.TEXT,
                userId = user.userId
            )
        )

        // get ai's recommend subject
        val recommendSubjects = chatAiService.getRecommendSubjects()

        return ChatRoomInitDto.toChatRoomDto(
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
        val user = getUser()
        val chatRoom = getChatRoom(chatRoomId)
        validIsUserAuthorizedForChatRoom(user.userId, chatRoom)
        validIsChatRoomAlreadyAnalysis(chatRoom)

        // save user's chat
        validStringLength(request.content, CONTENT_LENGTH, ErrorStatus.CHAT_CONTENT_OVER_FLOW)
        chatRepository.save(
            Chat(
                author = ChatAuthor.HUMAN,
                content = request.content,
                chatRoom = chatRoom
            )
        )

        // get ai's response
        val response = chatAiService.getChatResponse(request.content)
        validStringLength(response, CONTENT_LENGTH, ErrorStatus.CHAT_AI_CONTENT_OVER_FLOW)
        val aiChat = chatRepository.save(
            Chat(
                author = ChatAuthor.AI,
                content = response,
                chatRoom = chatRoom
            )
        )

        return ChatDto.toChatDto(aiChat)
    }

    /**
     * Delete ChatRoom
     *
     * @param id of chatroom for delete
     */
    @Transactional
    override fun deleteChatRoom(chatRoomId: Long) {
        val user = getUser()
        val chatRoom = getChatRoom(chatRoomId)
        validIsUserAuthorizedForChatRoom(user.userId, chatRoom)

        chatRepository.deleteByChatRoomId(chatRoomId)
        chatRoomRepository.deleteByChatRoomId(chatRoomId)
    }

    /**
     * Analysis Chat Room
     * Get Title And Comments For Each Chat From AI
     *
     * @param id of chatroom
     * @return ChatRoomDetailDto chatRoom info and chat content, chat author, chat comment list
     */
    @Transactional
    override fun analysisChatRoom(chatRoomId: Long): ChatRoomDetailDto {
        val user = getUser()
        val chatRoom = getChatRoom(chatRoomId)
        validIsUserAuthorizedForChatRoom(user.userId, chatRoom)
        validIsChatRoomAlreadyAnalysis(chatRoom)

        // call AI for analysis chat room and get title
        val chatList = chatRoom.chats
        val chatDtoList = chatList.map { ChatDto.toChatDto(it) }
        val chatAnalysisResponse = chatAiService.analysisChatRoom(chatDtoList)

        // save chat room title
        validStringLength(chatAnalysisResponse.title, TITLE_LENGTH, ErrorStatus.CHAT_ROOM_TITLE_OVER_FLOW)
        chatRoom.saveTitle(chatAnalysisResponse.title)

        // save analysis comments
        chatList
            .filter { it.author == ChatAuthor.HUMAN }
            .forEach { chat ->
                val comment = chatAnalysisResponse.chatList[chat.chatId.toString()]
                if (comment != null) {
                    validStringLength(comment, CONTENT_LENGTH, ErrorStatus.CHAT_CONTENT_OVER_FLOW)
                    chat.updateComment(comment)
                }
        }

        chatRoomRepository.save(chatRoom)
        chatRepository.saveAll(chatList)

        return ChatRoomDetailDto.toChatRoomDetailDto(chatRoom, chatList.sortedBy { it.chatId })
    }

    private fun validIsChatRoomAlreadyAnalysis(chatRoom: ChatRoom) {
        require(chatRoom.title != null) { throw GeneralException(ErrorStatus.ALREADY_ANALYSIS_CHAT_ROOM) }
    }

    /**
     * Get User's Archived Chat Room List
     *
     * @return ChatRoomListDto id, title, created info of each chatRoom
     */
    override fun getArchivedChatRoomList(): ChatRoomListDto {
        val user = getUser()
        val chatRoomList = chatRoomRepository.findArchivedChatRoomList(user.userId)

        return ChatRoomListDto.toChatRoomListDto(chatRoomList)
    }

    /**
     * Get Archived Chat Room's Detail
     *
     * @param chatRoomId id of chatRoom
     * @return ChatRoomDetailDto chatRoom info and chat content, chat author, chat comment list
     */
    override fun getChatRoomDetail(chatRoomId: Long): ChatRoomDetailDto {
        val user = getUser()
        val chatRoom = getChatRoom(chatRoomId)
        validIsUserAuthorizedForChatRoom(user.userId, chatRoom)

        val chatList = chatRoom.chats
        return ChatRoomDetailDto.toChatRoomDetailDto(chatRoom, chatList)
    }

    private fun validStringLength(content: String, length: Int, errorStatus: ErrorStatus) {
        require(content.length <= length) { throw GeneralException(errorStatus) }
    }

    private fun validIsUserAuthorizedForChatRoom(userId: Long, chatRoom: ChatRoom) {
        require(chatRoom.userId != userId) { throw GeneralException(ErrorStatus.FORBIDDEN_FOR_CHAT_ROOM) }
    }

    private fun getChatRoom(chatRoomId: Long): ChatRoom {
        return chatRoomRepository.findByChatRoomId(chatRoomId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_CHAT_ROOM)
    }

    private fun getUser(): User {
        // TODO: get user info
        return User(username = "username")
    }

}