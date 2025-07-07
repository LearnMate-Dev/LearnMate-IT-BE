package learn_mate_it.dev.domain.diary.application.service

interface FeedbackAIService {

    suspend fun postAnalysisFeedback(content: String): String

}