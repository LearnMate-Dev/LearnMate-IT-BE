package learn_mate_it.dev.domain.chat.application.dto.response

import learn_mate_it.dev.domain.chat.domain.model.ChatRoom


data class ChatRoomListDto(
    val chatRoomList : List<ChatRoomInfoDto>
) {
    companion object {
        fun toChatRoomListDto(
            chatRoomList: List<ChatRoom>
        ): ChatRoomListDto {
            return ChatRoomListDto(
                chatRoomList = chatRoomList.map { ChatRoomInfoDto.toChatRoomDto(it) }
            )
        }
    }
}