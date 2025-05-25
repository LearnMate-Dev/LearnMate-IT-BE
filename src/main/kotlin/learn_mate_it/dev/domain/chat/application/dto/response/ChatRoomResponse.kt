package learn_mate_it.dev.domain.chat.application.dto.response

import learn_mate_it.dev.domain.chat.domain.model.Chat
import learn_mate_it.dev.domain.chat.domain.model.ChatRoom

data class ChatRoomInitDto(
    val chatRoomId: Long,
    val recommendSubjects: List<String>?
) {
    companion object {
        fun toChatRoomDto(
            chatRoomId: Long,
            recommendSubjects: List<String>?
        ): ChatRoomInitDto {
            return ChatRoomInitDto(
                chatRoomId = chatRoomId,
                recommendSubjects = recommendSubjects
            )
        }
    }
}

data class ChatRoomInfoDto(
    val chatRoomId : Long,
    val title: String,
    val createdAt: String
) {
    companion object {
        fun toChatRoomDto(
            chatRoom: ChatRoom
        ): ChatRoomInfoDto {
            return ChatRoomInfoDto(
                chatRoomId = chatRoom.chatRoomId,
                title = chatRoom.title!!,
                createdAt = chatRoom.getCreatedAtFormatted()
            )
        }
    }
}

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

data class ChatRoomDetailDto(
    val chatRoom: ChatRoomInfoDto,
    val chatList: List<ChatDetailDto>
) {
    companion object {
        fun toChatRoomDetailDto(
            chatRoom: ChatRoom,
            chatList: List<Chat>
        ): ChatRoomDetailDto {
            return ChatRoomDetailDto(
                chatRoom = ChatRoomInfoDto.toChatRoomDto(chatRoom),
                chatList = chatList.map { ChatDetailDto.toChatDetailDto(it) }
            )
        }
    }
}