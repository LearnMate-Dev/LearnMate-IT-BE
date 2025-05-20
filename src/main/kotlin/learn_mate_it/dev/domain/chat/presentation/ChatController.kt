package learn_mate_it.dev.domain.chat.presentation

import learn_mate_it.dev.domain.chat.application.ChatService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController (
    private val chatService: ChatService
){
}