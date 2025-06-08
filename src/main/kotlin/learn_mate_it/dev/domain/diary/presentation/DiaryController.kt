package learn_mate_it.dev.domain.diary.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryAnalysisDto
import learn_mate_it.dev.domain.diary.application.service.DiaryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/diaries")
class DiaryController(
    private val diaryService: DiaryService
) {

    @PostMapping
    fun postAndAnalysisDiary(
        @RequestBody diaryRequest: PostDiaryDto
    ): ResponseEntity<ApiResponse<DiaryAnalysisDto>> {
        val response = diaryService.postAndAnalysisDiary(diaryRequest)
        return ApiResponse.success(SuccessStatus.CREATE_AND_ANALYSIS_DIARY_SUCCESS, response)
    }
}