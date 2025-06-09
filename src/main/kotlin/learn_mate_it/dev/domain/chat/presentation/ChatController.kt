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
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chats")
class ChatController (
    private val chatService: ChatService
){

    @PostMapping("/text")
    fun startTextChat(
    ): ResponseEntity<ApiResponse<ChatRoomInitDto>> {
        val response = chatService.startTextChat()
        return ApiResponse.success(SuccessStatus.START_TEXT_CHAT_SUCCESS, response)
    }

    @PostMapping("/text/{chatRoomId}")
    fun chatWithText(
        @PathVariable chatRoomId: Long,
        @RequestBody chatRequest: ChatRequest
    ): ResponseEntity<ApiResponse<ChatDto>> {
        val response = chatService.chatWithText(chatRoomId, chatRequest)
        return ApiResponse.success(SuccessStatus.CHAT_WITH_TEXT_SUCCESS, response)
    }

    @DeleteMapping("/{chatRoomId}")
    fun deleteChatRoom(
        @PathVariable chatRoomId: Long
    ): ResponseEntity<ApiResponse<String>> {
        chatService.deleteChatRoom(chatRoomId)
        return ApiResponse.success(SuccessStatus.DELETE_CHAT_ROOM_SUCCESS)
    }

    @PostMapping("/{chatRoomId}/analysis")
    fun analysisChatRoom(
        @PathVariable chatRoomId: Long,
    ): ResponseEntity<ApiResponse<ChatRoomDetailDto>> {
        val response = chatService.analysisChatRoom(chatRoomId)
        return ApiResponse.success(SuccessStatus.ANALYSIS_CHAT_ROOM_SUCCESS, response)
    }

    @GetMapping
    fun getArchivedChatRoomList(
    ): ResponseEntity<ApiResponse<ChatRoomListDto>> {
        val response = chatService.getArchivedChatRoomList()
        return ApiResponse.success(SuccessStatus.GET_ARCHIVED_CHAT_ROOM_LIST_SUCCESS, response)
    }

    @GetMapping("/{chatRoomId}")
    fun getChatRoomDetail(
        @PathVariable chatRoomId: Long
    ): ResponseEntity<ApiResponse<ChatRoomDetailDto>> {
        val response = chatService.getChatRoomDetail(chatRoomId)
        return ApiResponse.success(SuccessStatus.GET_ARCHIVED_CHAT_ROOM_SUCCESS, response)
    }

}