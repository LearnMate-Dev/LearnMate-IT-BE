package learn_mate_it.dev.domain.diary.application.service.impl

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.service.FeedbackService
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.SpellingFeedback
import learn_mate_it.dev.domain.diary.domain.repository.SpellingFeedbackRepository
import org.springframework.stereotype.Service

@Service
class FeedbackServiceImpl(
    private val feedbackRepository: SpellingFeedbackRepository,
): FeedbackService {

    @Transactional
    override fun saveFeedback(diary: Diary, content: String): SpellingFeedback {
        // save feedback entity
        return feedbackRepository.save(
            SpellingFeedback(
                content = content,
                diary = diary
            )
        )
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