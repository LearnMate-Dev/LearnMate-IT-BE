package learn_mate_it.dev.domain.chat.application.service

import learn_mate_it.dev.domain.chat.application.dto.request.ChatArchiveRequest
import learn_mate_it.dev.domain.chat.application.dto.request.ChatRequest
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDto

interface ChatService {

    fun startTextChat(): ChatRoomDto
    fun chatWithText(chatRoomId: Long, request: ChatRequest): ChatDto
    fun deleteChatRoom(chatRoomId: Long)

    fun archiveChatRoom(chatRoomId: Long, request: ChatArchiveRequest)

}