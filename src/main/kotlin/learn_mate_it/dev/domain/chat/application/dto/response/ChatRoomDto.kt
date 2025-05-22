package learn_mate_it.dev.domain.chat.application.dto.response

data class ChatRoomDto(
    val chatRoomId: Long,
    val recommendSubjects: List<String>?
) {
    companion object {
        fun toChatRoomDto(
            chatRoomId: Long,
            recommendSubjects: List<String>?
        ): ChatRoomDto {
            return ChatRoomDto(
                chatRoomId=chatRoomId,
                recommendSubjects = recommendSubjects
            )
        }
    }
}
