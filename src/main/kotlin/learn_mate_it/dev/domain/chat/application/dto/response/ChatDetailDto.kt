package learn_mate_it.dev.domain.chat.application.dto.response

import learn_mate_it.dev.domain.chat.domain.model.Chat

data class ChatDetailDto(
    val chatId: Long,
    val author: Int, // 0 : AI, 1 : HUMAN
    val content: String,
    val comment: String?,
    val createdAt: String
) {
    companion object {
        fun toChatDetailDto(
            chat: Chat
        ): ChatDetailDto {
            return ChatDetailDto(
                chatId = chat.chatId,
                author = chat.author.ordinal,
                content = chat.content,
                comment = chat.comment,
                createdAt = chat.getCreatedAtDetailedFormatted()
            )
        }
    }
}