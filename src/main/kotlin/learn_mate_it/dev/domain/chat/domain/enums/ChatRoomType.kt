package learn_mate_it.dev.domain.chat.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class ChatRoomType {
    VOICE, TEXT;

    companion object {
        fun from(value: String): ChatRoomType {
            return ChatRoomType.entries.find { it.toString() == value }
                ?: throw GeneralException(ErrorStatus.INVALID_CHAT_ROOM_TYPE)
        }
    }
}