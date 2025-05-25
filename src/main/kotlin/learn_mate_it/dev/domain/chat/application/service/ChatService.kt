package learn_mate_it.dev.domain.chat.application.service

import learn_mate_it.dev.domain.chat.application.dto.request.ChatArchiveRequest
import learn_mate_it.dev.domain.chat.application.dto.request.ChatRequest
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDetailDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomInitDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomListDto

interface ChatService {

    fun startTextChat(): ChatRoomInitDto
    fun chatWithText(chatRoomId: Long, request: ChatRequest): ChatDto
    fun deleteChatRoom(chatRoomId: Long)

    fun archiveChatRoom(chatRoomId: Long, request: ChatArchiveRequest)
    fun getArchivedChatRoomList(): ChatRoomListDto
    fun getChatRoomDetail(chatRoomId: Long): ChatRoomDetailDto

}