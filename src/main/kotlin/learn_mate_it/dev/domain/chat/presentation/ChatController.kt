package learn_mate_it.dev.domain.chat.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.chat.application.dto.request.ChatRequest
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDetailDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomInitDto
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomListDto
import learn_mate_it.dev.domain.chat.application.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chats")
class ChatController (
    private val chatService: ChatService
){

    @PostMapping("/text")
    fun startTextChat(
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<ChatRoomInitDto>> {
        val response = chatService.startTextChat(userId)
        return ApiResponse.success(SuccessStatus.START_TEXT_CHAT_SUCCESS, response)
    }

    @PostMapping("/text/{chatRoomId}")
    fun chatWithText(
        @AuthenticationPrincipal userId: Long,
        @PathVariable chatRoomId: Long,
        @RequestBody chatRequest: ChatRequest
    ): ResponseEntity<ApiResponse<ChatDto>> {
        val response = chatService.chatWithText(userId, chatRoomId, chatRequest)
        return ApiResponse.success(SuccessStatus.CHAT_WITH_TEXT_SUCCESS, response)
    }

    @DeleteMapping("/{chatRoomId}")
    fun deleteChatRoom(
        @AuthenticationPrincipal userId: Long,
        @PathVariable chatRoomId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        chatService.deleteChatRoom(userId, chatRoomId)
        return ApiResponse.success(SuccessStatus.DELETE_CHAT_ROOM_SUCCESS)
    }

    @PostMapping("/{chatRoomId}/analysis")
    fun analysisChatRoom(
        @AuthenticationPrincipal userId: Long,
        @PathVariable chatRoomId: Long,
    ): ResponseEntity<ApiResponse<ChatRoomDetailDto>> {
        val response = chatService.analysisChatRoom(userId, chatRoomId)
        return ApiResponse.success(SuccessStatus.ANALYSIS_CHAT_ROOM_SUCCESS, response)
    }

    @GetMapping
    fun getArchivedChatRoomList(
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<ChatRoomListDto>> {
        val response = chatService.getArchivedChatRoomList(userId)
        return ApiResponse.success(SuccessStatus.GET_ARCHIVED_CHAT_ROOM_LIST_SUCCESS, response)
    }

    @GetMapping("/{chatRoomId}")
    fun getChatRoomDetail(
        @AuthenticationPrincipal userId: Long,
        @PathVariable chatRoomId: Long
    ): ResponseEntity<ApiResponse<ChatRoomDetailDto>> {
        val response = chatService.getChatRoomDetail(userId, chatRoomId)
        return ApiResponse.success(SuccessStatus.GET_ARCHIVED_CHAT_ROOM_SUCCESS, response)
    }

}