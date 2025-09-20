package learn_mate_it.dev.domain.chat.infra.application.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


data class ChatRoomAnalysisDto @JsonCreator constructor(
    @JsonProperty("title") val title: String,
    @JsonProperty("chatList") val chatList: Map<String, String>
)