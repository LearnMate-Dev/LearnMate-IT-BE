package learn_mate_it.dev.domain.diary.application.service.impl

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryCalendarDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.SimpleDiaryDto
import learn_mate_it.dev.domain.diary.application.service.*
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.repository.DiaryRepository
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val spellingService: SpellingService,
    private val feedbackService: FeedbackService,

) : DiaryService {

    @Transactional
    override fun saveDiaryAndSpelling(
        userId: Long,
        content: String,
        spellingAnalysisResponse: SpellingAnalysisResponse?,
        feedbackResponse: String
    ): DiaryDto {
        val diary = saveDiary(userId, content)
        val (spelling, revisions) = spellingService.saveSpellingAndRevisions(diary, spellingAnalysisResponse)
        val feedback = feedbackService.saveFeedback(diary, feedbackResponse)

        return DiaryDto.toDiaryDto(diary, spelling, revisions, feedback.content)
    }

    @Transactional
    override fun saveDiary(userId: Long, content: String): Diary {
        return diaryRepository.save(
            Diary(
                content = content,
                userId = userId,
            )
        )
    }

    /**
     * Get Diary's Detail And Spelling Info
     *
     * @param diaryId
     * @return
     */
    override fun getDiaryDetail(userId: Long, diaryId: Long): DiaryDto {
        val diary = getDiaryFetchSpelling(diaryId)
        diary.validIsUserAuthorized(userId)
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
        diary.validIsUserAuthorized(userId)
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
        diary.validIsUserAuthorized(userId)

        spellingService.deleteByDiaryId(diaryId)
        feedbackService.deleteByDiaryId(diaryId)
        diaryRepository.deleteByDiaryId(diaryId)
    }

    @Transactional
    override fun deleteByUserId(userId: Long) {
        spellingService.deleteByUserId(userId)
        feedbackService.deleteByUserId(userId)
        diaryRepository.deleteByUserId(userId)
    }

    private fun getDiary(diaryId: Long): Diary {
        return diaryRepository.findByDiaryId(diaryId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_DIARY)
    }

}