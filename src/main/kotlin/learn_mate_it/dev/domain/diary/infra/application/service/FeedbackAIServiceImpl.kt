package learn_mate_it.dev.domain.diary.infra.application.service

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.common.util.ResourceLoader
import learn_mate_it.dev.domain.diary.application.service.FeedbackAIService
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service

@Service
class FeedbackAIServiceImpl(
    private val chatModel: OpenAiChatModel,
    private val resourceLoader: ResourceLoader
): FeedbackAIService {

    override fun postAnalysisFeedback(content: String): String {
        val ANALYSIS_FEEDBACK_PROMPT = resourceLoader.getResourceContent("analysis-feedback-prompt.txt")
        try {
            val response = chatModel.call(ANALYSIS_FEEDBACK_PROMPT + content)
            return response.trim()
        } catch (e: Exception) {
            throw GeneralException(ErrorStatus.ANALYSIS_FEEDBACK_SERVER_ERROR)
        }
    }

}