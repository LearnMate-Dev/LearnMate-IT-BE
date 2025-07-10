package learn_mate_it.dev.domain.diary.application.service.impl

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import learn_mate_it.dev.domain.diary.application.service.*
import learn_mate_it.dev.domain.diary.domain.repository.DiaryRepository
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaryAnalysisServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val diaryService: DiaryService,
    private val spellingAnalysisService: SpellingAnalysisService,
    private val feedbackAIService: FeedbackAIService
) : DiaryAnalysisService {

    private val log = LoggerFactory.getLogger(this::class.java)
    private final val CONTENT_LENGTH: Int = 500

    /**
     * Analysis Diary's Spelling And Get Feedback From AI About Spelling
     * Diary's Title will be the date
     *
     * @param content content of diary
     * @return DiaryAnalysisDto content of diary and analysis about diary (score, spelling comment, examples)
     */
    override fun analysisDiary(userId: Long, content: String): DiaryDto {
        validNotWrittenToday(userId)
        validStringLength(content, CONTENT_LENGTH, ErrorStatus.DIARY_CONTENT_OVER_FLOW)

        val (spellingAnalysis, feedback) = runBlocking {
            val spellingDeferred = async { spellingAnalysisService.postAnalysisSpelling(content) }
            val feedbackDeferred = async { feedbackAIService.postAnalysisFeedback(content) }

            spellingDeferred.await() to feedbackDeferred.await()
        }

        return diaryService.saveDiaryAndSpelling(userId, content, spellingAnalysis, feedback)
    }

    private fun validNotWrittenToday(userId: Long) {
        val startDay = LocalDate.now().atStartOfDay()
        val endDay = startDay.plusDays(1)

        val isWrittenToday = diaryRepository.existsByUserIdAndCreatedAt(userId, startDay, endDay)
        require(!isWrittenToday) { throw GeneralException(ErrorStatus.ALREADY_DIARY_WRITTEN) }
    }

    private fun validStringLength(content: String, length: Int, errorStatus: ErrorStatus) {
        require(content.length <= length) { throw GeneralException(errorStatus) }
    }

}