package learn_mate_it.dev.domain.chat.application.dto.request

data class ChatRequest(
    val content: String
)

data class ChatArchiveRequest(
    val title: String
)
