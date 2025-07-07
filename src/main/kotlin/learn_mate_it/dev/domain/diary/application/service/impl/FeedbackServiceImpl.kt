package learn_mate_it.dev.domain.diary.application.service.impl

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.service.FeedbackAIService
import learn_mate_it.dev.domain.diary.application.service.FeedbackService
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.SpellingFeedback
import learn_mate_it.dev.domain.diary.domain.repository.SpellingFeedbackRepository
import org.springframework.stereotype.Service

@Service
class FeedbackServiceImpl(
    private val feedbackRepository: SpellingFeedbackRepository,
    private val feedbackAIService: FeedbackAIService
): FeedbackService {

    private final val FEEDBACK_CONTENT_LENGTH = 700

    @Transactional
    override fun analysisFeedback(diary: Diary, content: String): String {
        // get feedback about spelling from ai
        val response = feedbackAIService.postAnalysisFeedback(content)
        validStringLength(response, FEEDBACK_CONTENT_LENGTH, ErrorStatus.SPELLING_FEEDBACK_OVER_FLOW)

        // save feedback entity
        feedbackRepository.save(
            SpellingFeedback(
                content = response,
                diary = diary
            )
        )

        return response
    }

    @Transactional
    override fun deleteByUserId(userId: Long) {
        feedbackRepository.deleteByUserId(userId)
    }

    @Transactional
    override fun deleteByDiaryId(diaryId: Long) {
        feedbackRepository.deleteByDiaryId(diaryId)
    }

    private fun validStringLength(content: String, length: Int, errorStatus: ErrorStatus) {
        require(content.length <= length) { throw GeneralException(errorStatus) }
    }

}