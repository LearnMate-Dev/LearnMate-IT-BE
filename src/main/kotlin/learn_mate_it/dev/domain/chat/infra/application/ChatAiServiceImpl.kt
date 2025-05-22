package learn_mate_it.dev.domain.chat.infra.application

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.common.util.ResourceLoader
import learn_mate_it.dev.domain.chat.application.service.ChatAiService
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service

@Service
class ChatAiServiceImpl(
    private val chatModel: OpenAiChatModel,
    private val resourceLoader: ResourceLoader
) : ChatAiService {

    override fun getRecommendSubjects(): List<String> {
        val RECOMMEND_SUBJECT_PROMPT: String = resourceLoader.getResourceContent("recommend-subject-prompt.txt")
        try {
            val response: String = chatModel.call(RECOMMEND_SUBJECT_PROMPT)
            return response.split("\n").map { it.trim() }
        } catch (e: Exception) {
            throw GeneralException(ErrorStatus.CHAT_AI_SERVER_ERROR)
        }
    }

}