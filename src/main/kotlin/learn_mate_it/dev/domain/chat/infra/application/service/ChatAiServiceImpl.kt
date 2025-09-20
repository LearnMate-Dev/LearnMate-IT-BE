package learn_mate_it.dev.domain.chat.infra.application.service

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.common.util.ResourceLoader
import learn_mate_it.dev.domain.chat.application.dto.response.ChatDto
import learn_mate_it.dev.domain.chat.application.service.ChatAiService
import learn_mate_it.dev.domain.chat.infra.application.dto.response.ChatRoomAnalysisDto
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service

@Service
class ChatAiServiceImpl(
    private val chatModel: OpenAiChatModel,
    private val resourceLoader: ResourceLoader,
) : ChatAiService {

    private val chatClient = ChatClient.builder(chatModel).build()
    private val log = LoggerFactory.getLogger("Logger")
    private val RECOMMEND_SUBJECT_PROMPT: String = resourceLoader.getResourceContent("recommend-subject-prompt.txt")
    private val CHAT_PROMPT = resourceLoader.getResourceContent("chat-prompt.txt")
    private val ANALYSIS_CHAT_PROMPT = resourceLoader.getResourceContent("analysis-chat-prompt.txt")

    override fun getRecommendSubjects(): List<String> {
        try {
            val response = chatModel.call(RECOMMEND_SUBJECT_PROMPT)
            return response.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
        } catch (e: Exception) {
            throw GeneralException(ErrorStatus.CHAT_AI_SERVER_ERROR)
        }
    }

    override fun getChatResponse(content: String): String {
        try {
            val response = chatModel.call(CHAT_PROMPT + content)
            return response.trim()
        } catch (e: Exception) {
            throw GeneralException(ErrorStatus.CHAT_AI_SERVER_ERROR)
        }
    }

    override fun analysisChatRoom(chatList: List<ChatDto>): ChatRoomAnalysisDto {
        try {
            return chatClient.prompt()
                .user(ANALYSIS_CHAT_PROMPT + chatList.toString())
                .call()
                .entity(ChatRoomAnalysisDto::class.java)
        } catch (e: Exception) {
            log.error("[*] AI 채팅방 내용 요약 요청 중 오류 발생 : ", e)
            throw GeneralException(ErrorStatus.CHAT_AI_ANALYSIS_ERROR)
        }
    }

}