package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryAnalysisDto

interface DiaryService {

    fun postAndAnalysisDiary(userId: Long, diaryRequest: PostDiaryDto): DiaryAnalysisDto

}