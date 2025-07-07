package learn_mate_it.dev.domain.diary.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryCalendarDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import learn_mate_it.dev.domain.diary.application.service.DiaryAnalysisService
import learn_mate_it.dev.domain.diary.application.service.DiaryService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/diaries")
class DiaryController(
    private val diaryService: DiaryService,
    private val diaryAnalysisService: DiaryAnalysisService
) {

    @PostMapping
    fun postAndAnalysisDiary(
        @AuthenticationPrincipal userId: Long,
        @RequestBody diaryRequest: PostDiaryDto
    ): ResponseEntity<ApiResponse<DiaryDto>> {
        val response = diaryAnalysisService.analysisDiary(userId, diaryRequest.content)
        return ApiResponse.success(SuccessStatus.CREATE_AND_ANALYSIS_DIARY_SUCCESS, response)
    }

    @DeleteMapping("/{diaryId}")
    fun deleteDiary(
        @AuthenticationPrincipal userId: Long,
        @PathVariable("diaryId") diaryId: Long,
    ): ResponseEntity<ApiResponse<Nothing>> {
        diaryService.deleteDiary(userId, diaryId)
        return ApiResponse.success(SuccessStatus.DELETE_DIARY_SUCCESS)
    }

    @GetMapping("/{diaryId}")
    fun getDiaryDetail(
       @AuthenticationPrincipal userId: Long,
       @PathVariable("diaryId") diaryId: Long
    ): ResponseEntity<ApiResponse<DiaryDto>> {
        val response = diaryService.getDiaryDetail(userId, diaryId)
        return ApiResponse.success(SuccessStatus.GET_DIARY_DETAIL_SUCCESS, response)
    }

    @GetMapping
    fun getDiaryDetailByDate(
        @AuthenticationPrincipal userId: Long,
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<ApiResponse<DiaryDto>> {
        val response = diaryService.getDiaryDetailByDate(userId, date)
        return ApiResponse.success(SuccessStatus.GET_DIARY_DETAIL_SUCCESS, response)
    }

    @GetMapping("/calendar")
    fun getDiaryCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Int,
    ): ResponseEntity<ApiResponse<DiaryCalendarDto>> {
        val response = diaryService.getDiaryCalendar(userId, year, month)
        return ApiResponse.success(SuccessStatus.GET_DIARY_CALENDAR_SUCCESS, response)
    }

}