package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto

interface DiaryAnalysisService {

    fun analysisDiary(userId: Long, content: String): DiaryDto

}