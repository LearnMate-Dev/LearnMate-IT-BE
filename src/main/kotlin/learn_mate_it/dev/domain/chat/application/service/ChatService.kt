package learn_mate_it.dev.domain.chat.application.service

import learn_mate_it.dev.domain.chat.application.dto.request.ChatRequest
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDetailDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomInitDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomListDto

interface ChatService {

    fun startTextChat(userId: Long): ChatRoomInitDto
    fun chatWithText(userId: Long, chatRoomId: Long, request: ChatRequest): ChatDto
    fun deleteChatRoom(userId: Long, chatRoomId: Long)
    fun analysisChatRoom(userId: Long, chatRoomId: Long): ChatRoomDetailDto

    fun getArchivedChatRoomList(userId: Long): ChatRoomListDto
    fun getChatRoomDetail(userId: Long, chatRoomId: Long): ChatRoomDetailDto

    fun deleteByUserId(userId: Long)

}