package learn_mate_it.dev.domain.chat.application.dto.response

import learn_mate_it.dev.domain.chat.domain.model.Chat

data class ChatDto(
    val chatId: Long,
    val content: String
) {
    companion object {
        fun toChatDto(
            response: Chat
        ): ChatDto {
            return ChatDto(
                chatId = response.chatId,
                content = response.content
            )
        }
    }
}