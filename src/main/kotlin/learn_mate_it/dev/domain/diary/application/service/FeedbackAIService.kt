package learn_mate_it.dev.domain.diary.application.service

interface FeedbackAIService {

    fun postAnalysisFeedback(content: String): String

}