package learn_mate_it.dev.domain.diary.application.service.impl

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import learn_mate_it.dev.domain.diary.application.service.*
import learn_mate_it.dev.domain.diary.domain.repository.DiaryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaryAnalysisServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val diaryService: DiaryService,
    private val spellingService: SpellingService,
    private val feedbackService: FeedbackService,
    private val spellingAnalysisService: SpellingAnalysisService,
    private val feedbackAIService: FeedbackAIService
): DiaryAnalysisService {

    private val log = LoggerFactory.getLogger(this::class.java)
    private final val CONTENT_LENGTH: Int = 500

    /**
     * Analysis Diary's Spelling And Get Feedback From AI About Spelling
     * Diary's Title will be the date
     *
     * @param content content of diary
     * @return DiaryAnalysisDto content of diary and analysis about diary (score, spelling comment, examples)
     */
    override suspend fun analysisDiary(userId: Long, content: String): DiaryDto = coroutineScope {
        log.info("analysis diary start")
        validNotWrittenToday(userId)
        validStringLength(content, CONTENT_LENGTH, ErrorStatus.DIARY_CONTENT_OVER_FLOW)
        log.info("success for valid diary")

        // call api async
        val spellingAnalysisResponse = async {
            runCatching {
                spellingAnalysisService.postAnalysisSpelling(content)
            }.getOrElse {
                log.error("Could not parse spelling analysis: {}", it)
                throw GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR)
            }
        }.await()

        val feedbackResponse = async {
            runCatching {
                feedbackAIService.postAnalysisFeedback(content)
            }.getOrElse {
                log.error("Could not parse feedback: {}", it)
                throw GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR)
            }
        }.await()
        log.info("success to await")

        // save entities
        val diary = diaryService.saveDiary(userId, content)
        log.info("success to save diary")
        val spelling = spellingService.saveSpellingAndRevisions(diary, spellingAnalysisResponse)
        log.info("success to save spelling")
        feedbackService.saveFeedback(diary, feedbackResponse)
        log.info("success to save feedback")

        return@coroutineScope DiaryDto.toDiaryDto(diary, spelling, spelling.revisions, feedbackResponse)
    }

    private suspend fun validNotWrittenToday(userId: Long) {
        val startDay = LocalDate.now().atStartOfDay()
        val endDay = startDay.plusDays(1)

        val isWrittenToday = diaryRepository.existsByUserIdAndCreatedAt(userId, startDay, endDay)

        if(isWrittenToday) { throw GeneralException(ErrorStatus.ALREADY_DIARY_WRITTEN) }
    }

    private suspend fun validStringLength(content: String, length: Int, errorStatus: ErrorStatus) {
        require(content.length <= length) { throw GeneralException(errorStatus) }
    }

}