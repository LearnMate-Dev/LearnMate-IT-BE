package learn_mate_it.dev.domain.diary.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto
import learn_mate_it.dev.domain.diary.application.service.DiaryService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/diaries")
class DiaryController(
    private val diaryService: DiaryService
) {

    @PostMapping
    fun postAndAnalysisDiary(
        @AuthenticationPrincipal userId: Long,
        @RequestBody diaryRequest: PostDiaryDto
    ): ResponseEntity<ApiResponse<DiaryDto>> {
        val response = diaryService.postAndAnalysisDiary(userId, diaryRequest)
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

}