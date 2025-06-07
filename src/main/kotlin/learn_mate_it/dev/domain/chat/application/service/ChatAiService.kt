package learn_mate_it.dev.domain.chat.application.service

import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.infra.application.dto.response.ChatRoomAnalysisDto

interface ChatAiService {

    fun getRecommendSubjects(): List<String>
    fun getChatResponse(content: String): String
    fun analysisChatRoom(chatList: List<ChatDto>): ChatRoomAnalysisDto

}