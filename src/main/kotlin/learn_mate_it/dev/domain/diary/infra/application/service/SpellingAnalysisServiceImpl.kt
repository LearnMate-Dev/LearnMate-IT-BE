package learn_mate_it.dev.domain.diary.infra.application.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.common.util.ResourceLoader
import learn_mate_it.dev.domain.diary.application.service.SpellingAnalysisService
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.slf4j.LoggerFactory
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service

@Service
class SpellingAnalysisServiceImpl(
    private val chatModel: OpenAiChatModel,
    private val resourceLoader: ResourceLoader,
    private val objectMapper: ObjectMapper
): SpellingAnalysisService {

    private val log = LoggerFactory.getLogger("Logger")
    private val ANALYSIS_SPELLING_PROMPT = resourceLoader.getResourceContent("analysis-spelling-prompt.txt")

    override suspend fun postAnalysisSpelling(content: String): SpellingAnalysisResponse = withContext(Dispatchers.IO) {
        try {
            val response = chatModel.call(ANALYSIS_SPELLING_PROMPT + content)
            parseAiResponse(response)
        } catch (e: Exception) {
            log.error("[*] AI 맞춤법 검사 요청 중 오류 발생 : ", e)
            throw GeneralException(ErrorStatus.ANALYSIS_SPELLING_SERVER_ERROR)
        }
    }

    private inline fun <reified T> parseAiResponse(aiResponse: String): T {
        return try {
            val cleanResponse = aiResponse.replace("```json\\s*".toRegex(), "").replace("```".toRegex(), "");
            objectMapper.readValue(cleanResponse, T::class.java)
        } catch (e: JsonProcessingException) {
            log.error("[*] AI 맞춤법 검사 결과 파싱 중 오류 발생 : ", e)
            throw GeneralException(ErrorStatus.ANALYSIS_SPELLING_AI_PARSING_ERROR)
        }
    }
}