package learn_mate_it.dev.domain.chat.application.dto.response

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
                chatRoomId=chatRoomId,
                recommendSubjects = recommendSubjects
            )
        }
    }
}
