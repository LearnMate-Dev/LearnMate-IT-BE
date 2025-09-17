package learn_mate_it.dev.domain.diary.infra.application.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.common.util.ResourceLoader
import learn_mate_it.dev.domain.diary.application.service.FeedbackAIService
import org.slf4j.LoggerFactory
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service

@Service
class FeedbackAIServiceImpl(
    private val chatModel: OpenAiChatModel,
    private val resourceLoader: ResourceLoader
): FeedbackAIService {

    private val log = LoggerFactory.getLogger("Logger")
    private val ANALYSIS_FEEDBACK_PROMPT = resourceLoader.getResourceContent("analysis-feedback-prompt.txt")

    override suspend fun postAnalysisFeedback(content: String): String = withContext(Dispatchers.IO) {
        try {
            val response = chatModel.call(ANALYSIS_FEEDBACK_PROMPT + content)
            response.trim()
        } catch (e: Exception) {
            log.error("[*] AI 피드백 생성 중 오류 발생 : ", e)
            throw GeneralException(ErrorStatus.ANALYSIS_FEEDBACK_SERVER_ERROR)
        }
    }
}