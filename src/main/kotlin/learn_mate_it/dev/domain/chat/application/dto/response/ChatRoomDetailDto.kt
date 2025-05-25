package learn_mate_it.dev.domain.chat.application.dto.response

import learn_mate_it.dev.domain.chat.domain.model.Chat

data class ChatRoomDetailDto(
    val chatRoomId: Long,
    val chatList: List<ChatDetailDto>
) {
    companion object {
        fun toChatRoomDetailDto(
            chatRoomId: Long,
            chatList: List<Chat>
        ): ChatRoomDetailDto {
            return ChatRoomDetailDto(
                chatRoomId = chatRoomId,
                chatList = chatList.map { ChatDetailDto.toChatDetailDto(it) }
            )
        }
    }
}
