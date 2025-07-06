package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryCalendarDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import java.time.LocalDate
import java.util.*

interface DiaryService {

    fun postAndAnalysisDiary(userId: Long, diaryRequest: PostDiaryDto): DiaryDto
    fun getDiaryDetail(userId: Long, diaryId: Long): DiaryDto
    fun getDiaryDetailByDate(userId: Long, date: LocalDate): DiaryDto
    fun getDiaryCalendar(userId: Long, year: Int, month: Int): DiaryCalendarDto

    fun deleteByUserId(userId: Long)
    fun deleteDiary(userId: Long, diaryId: Long)

}