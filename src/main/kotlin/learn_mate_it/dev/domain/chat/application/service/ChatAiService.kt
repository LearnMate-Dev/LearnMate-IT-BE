package learn_mate_it.dev.domain.chat.application.service

interface ChatAiService {

    fun getRecommendSubjects(): List<String>
    fun getChatResponse(content: String): String

}