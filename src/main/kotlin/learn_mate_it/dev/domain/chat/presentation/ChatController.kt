package learn_mate_it.dev.domain.chat.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.chat.application.dto.response.ChatRoomDto
import learn_mate_it.dev.domain.chat.application.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}