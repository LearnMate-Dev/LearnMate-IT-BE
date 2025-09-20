package learn_mate_it.dev.domain.diary.infra.application.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.common.util.ResourceLoader
import learn_mate_it.dev.domain.diary.application.service.SpellingAnalysisService
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service

@Service
class SpellingAnalysisServiceImpl(
    private val chatModel: OpenAiChatModel,
    private val resourceLoader: ResourceLoader
): SpellingAnalysisService {

    private val chatClient = ChatClient.builder(chatModel).build()
    private val log = LoggerFactory.getLogger("Logger")
    private val ANALYSIS_SPELLING_PROMPT = resourceLoader.getResourceContent("analysis-spelling-prompt.txt")

    override suspend fun postAnalysisSpelling(content: String): SpellingAnalysisResponse = withContext(Dispatchers.IO) {
        try {
            chatClient.prompt()
                .user(ANALYSIS_SPELLING_PROMPT + content)
                .call()
                .entity(SpellingAnalysisResponse::class.java)
        } catch (e: Exception) {
            log.error("[*] AI 맞춤법 검사 요청 중 오류 발생 : ", e)
            throw GeneralException(ErrorStatus.ANALYSIS_SPELLING_SERVER_ERROR)
        }
    }

}