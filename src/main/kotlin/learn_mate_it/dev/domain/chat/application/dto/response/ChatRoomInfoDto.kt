package learn_mate_it.dev.domain.chat.application.dto.response

import learn_mate_it.dev.domain.chat.domain.model.ChatRoom

data class ChatRoomInfoDto(
    val chatRoomId : Long,
    val title: String?,
    val createdAt: String
) {
    companion object {
        fun toChatRoomDto(
            chatRoom: ChatRoom
        ): ChatRoomInfoDto {
            return ChatRoomInfoDto(
                chatRoomId = chatRoom.chatRoomId,
                title = chatRoom.title,
                createdAt = chatRoom.getCreatedAtFormatted()
            )
        }
    }
}