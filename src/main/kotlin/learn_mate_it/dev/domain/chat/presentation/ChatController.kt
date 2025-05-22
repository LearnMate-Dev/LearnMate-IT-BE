package learn_mate_it.dev.domain.chat.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.chat.application.dto.request.ChatArchiveRequest
import learn_mate_it.dev.domain.chat.application.dto.request.ChatRequest
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDto
import learn_mate_it.dev.domain.chat.application.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chats")
class ChatController (
    private val chatService: ChatService
){

    @PostMapping("/text")
    fun startTextChat(): ResponseEntity<ApiResponse<ChatRoomDto>> {
        val response: ChatRoomDto = chatService.startTextChat()
        return ApiResponse.success(SuccessStatus.START_TEXT_CHAT_SUCCESS, response)
    }

    @PostMapping("/text/{chatRoomId}")
    fun chatWithText(
        @PathVariable chatRoomId: Long,
        @RequestBody chatRequest: ChatRequest
    ): ResponseEntity<ApiResponse<ChatDto>> {
        val response: ChatDto = chatService.chatWithText(chatRoomId, chatRequest)
        return ApiResponse.success(SuccessStatus.CHAT_WITH_TEXT_SUCCESS, response)
    }

    @DeleteMapping("/{chatRoomId}")
    fun deleteChatRoom(
        @PathVariable chatRoomId: Long
    ): ResponseEntity<ApiResponse<String>> {
        chatService.deleteChatRoom(chatRoomId)
        return ApiResponse.success(SuccessStatus.DELETE_CHAT_ROOM_SUCCESS)
    }

    @PatchMapping("/{chatRoomId}/archive")
    fun archiveChat(
        @PathVariable chatRoomId: Long,
        @RequestBody chatArchiveRequest: ChatArchiveRequest
    ): ResponseEntity<ApiResponse<String>> {
        chatService.archiveChatRoom(chatRoomId, chatArchiveRequest)
        return ApiResponse.success(SuccessStatus.ARCHIVE_CHAT_ROOM_SUCCESS)
    }

}