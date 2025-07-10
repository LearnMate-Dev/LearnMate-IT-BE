package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.application.dto.response.DiaryCalendarDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import java.time.LocalDate

interface DiaryService {

    fun saveDiary(userId: Long, content: String): Diary
    fun saveDiaryAndSpelling(userId: Long, content: String, spellingAnalysisResponse: SpellingAnalysisResponse, feedbackResponse: String): DiaryDto

    fun getDiaryDetail(userId: Long, diaryId: Long): DiaryDto
    fun getDiaryDetailByDate(userId: Long, date: LocalDate): DiaryDto
    fun getDiaryCalendar(userId: Long, year: Int, month: Int): DiaryCalendarDto

    fun deleteByUserId(userId: Long)
    fun deleteDiary(userId: Long, diaryId: Long)

}