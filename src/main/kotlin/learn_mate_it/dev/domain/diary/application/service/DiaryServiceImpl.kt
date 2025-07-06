package learn_mate_it.dev.domain.diary.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryCalendarDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.SimpleDiaryDto
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision
import learn_mate_it.dev.domain.diary.domain.repository.DiaryRepository
import learn_mate_it.dev.domain.diary.domain.repository.SpellingFeedbackRepository
import learn_mate_it.dev.domain.diary.domain.repository.SpellingRepository
import learn_mate_it.dev.domain.diary.domain.repository.SpellingRevisionRepository
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val spellingRepository: SpellingRepository,
    private val spellingRevisionRepository: SpellingRevisionRepository,
    private val spellingFeedbackRepository: SpellingFeedbackRepository,
    private val spellingService: SpellingService,
) : DiaryService {

    private final val CONTENT_LENGTH: Int = 500

    /**
     * Post Diary And Analysis Diary's Spelling And Get Feedback From AI About Spelling
     * Diary's Title will be the date
     *
     * @param diaryRequest content of diary
     * @return DiaryAnalysisDto content of diary and analysis about diary (score, spelling comment, examples)
     */
    @Transactional
    override fun postAndAnalysisDiary(userId: Long, diaryRequest: PostDiaryDto): DiaryDto {
        validNotWrittenToday(userId)
        validStringLength(diaryRequest.content, CONTENT_LENGTH, ErrorStatus.DIARY_CONTENT_OVER_FLOW)

        // save diary
        val diary = diaryRepository.save(
            Diary(
                content = diaryRequest.content,
                userId = userId,
            )
        )

        // get spelling analysis from api
        val spellingAnalysisResponse = spellingService.analysisSpelling(diary.content)
        val score = getSpellingScore(spellingAnalysisResponse)

        // save spelling entity
        val spelling = spellingRepository.save(
            Spelling(
                revisedContent = spellingAnalysisResponse.revised,
                score = score,
                diary = diary
            )
        )

        // save separate spelling analysis
        val revisions = spellingAnalysisResponse.revisedSentences
            ?.flatMap { it.revisedBlocks.orEmpty() }
            ?.flatMap { block ->
                block.revisions.map { revision ->
                    SpellingRevision(
                        originContent = block.origin.content,
                        beginOffset = block.origin.beginOffset,
                        revisedContent = revision.revised,
                        examples = revision.examples.toTypedArray(),
                        comment = revision.comment,
                        spelling = spelling
                    )
                }
            }.orEmpty()
        spellingRevisionRepository.saveAll(revisions)

        // TODO: feedback entity
        return DiaryDto.toDiaryDto(diary, spelling, revisions, null)
    }

    private fun validNotWrittenToday(userId: Long) {
        val startDay = LocalDate.now().atStartOfDay()
        val endDay = startDay.plusDays(1)

        val isWrittenToday = diaryRepository.existsByUserIdAndCreatedAt(userId, startDay, endDay)
        require(!isWrittenToday) { throw GeneralException(ErrorStatus.ALREADY_DIARY_WRITTEN)}
    }

    private fun getSpellingScore(analysisResponse: SpellingAnalysisResponse): Int {
        val sentences = analysisResponse.revisedSentences ?: return 100
        // TODO: feature score
        return 0
    }

    /**
     * Get Diary's Detail And Spelling Info
     *
     * @param diaryId
     * @return
     */
    override fun getDiaryDetail(userId: Long, diaryId: Long): DiaryDto {
        val diary = getDiaryFetchSpelling(diaryId)
        validIsUserAuthorizedForDiary(userId, diary)

        return DiaryDto.toDiaryDto(diary)
    }

    private fun getDiaryFetchSpelling(diaryId: Long): Diary {
        return diaryRepository.findByDiaryIdFetchSpelling(diaryId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_DIARY)
    }

    /**
     * Get Diary's Detail And Spelling Info By CreatedAt DateTime
     *
     * @param date LocalDate Value
     * @return
     */
    override fun getDiaryDetailByDate(userId: Long, date: LocalDate): DiaryDto {
        val diary = getDiaryByUserIdAndDateFetchSpelling(userId, date)

        return DiaryDto.toDiaryDto(diary)
    }

    private fun getDiaryByUserIdAndDateFetchSpelling(userId: Long, date: LocalDate): Diary {
        val startDay = date.atStartOfDay()
        val endDay = startDay.plusDays(1)

        return diaryRepository.findByUserIdAndCreatedAtFetchSpelling(userId, startDay, endDay)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_DATE_DIARY)
    }

    /**
     * Get Diary List Per Year & Month
     *
     * @param year
     * @param month
     * @return DiaryCalendar Each DiaryId And Score Of Spelling
     */
    override fun getDiaryCalendar(userId: Long, year: Int, month: Int): DiaryCalendarDto {
        validateDateParam(year, month)
        val diaryCalendar = getDiaryByUserIdAndYearAndMonth(userId, year, month)

        return DiaryCalendarDto(year, month, diaryCalendar)
    }

    private fun validateDateParam(year: Int, month: Int) {
        require(0 < year) { throw GeneralException(ErrorStatus.INVALID_YEAR_PARAM)}
        require(month in 1..12) { throw GeneralException(ErrorStatus.INVALID_MONTH_PARAM)}
    }

    private fun getDiaryByUserIdAndYearAndMonth(userId: Long, year: Int, month: Int): List<SimpleDiaryDto> {
        val date = LocalDate.of(year, month, 1)
        val startOfMonth = date.withDayOfMonth(1).atStartOfDay()
        val startOfNextMonth = date.plusMonths(1).withDayOfMonth(1).atStartOfDay()
        return diaryRepository.findByUserIdAndYearAndMonth(userId, startOfMonth, startOfNextMonth)
    }


    /**
     * Delete Diary By DiaryId
     *
     * @param diaryId
     */
    @Transactional
    override fun deleteDiary(userId: Long, diaryId: Long) {
        val diary = getDiary(diaryId)
        validIsUserAuthorizedForDiary(userId, diary)

        spellingRevisionRepository.deleteByDiaryId(diaryId)
        spellingFeedbackRepository.deleteByDiaryId(diaryId)
        spellingRepository.deleteByDiaryId(diaryId)
        diaryRepository.deleteByDiaryId(diaryId)
    }

    fun validIsUserAuthorizedForDiary(userId: Long, diary: Diary) {
        if (diary.userId != userId) {
            throw GeneralException(ErrorStatus.FORBIDDEN_FOR_DIARY)
        }
    }

    @Transactional
    override fun deleteByUserId(userId: Long) {
        spellingRevisionRepository.deleteByUserId(userId)
        spellingFeedbackRepository.deleteByUserId(userId)
        spellingRepository.deleteByUserId(userId)
        diaryRepository.deleteByUserId(userId)
    }

    private fun validStringLength(content: String, length: Int, errorStatus: ErrorStatus) {
        require(content.length <= length) { throw GeneralException(errorStatus) }
    }

    private fun getDiary(diaryId: Long): Diary {
        return diaryRepository.findByDiaryId(diaryId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_DIARY)
    }

}