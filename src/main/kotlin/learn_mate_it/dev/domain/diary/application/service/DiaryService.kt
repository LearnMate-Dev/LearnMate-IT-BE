package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryDto

interface DiaryService {

    fun postAndAnalysisDiary(userId: Long, diaryRequest: PostDiaryDto): DiaryDto
    fun getDiaryDetail(userId: Long, diaryId: Long): DiaryDto

    fun deleteByUserId(userId: Long)
    fun deleteDiary(userId: Long, diaryId: Long)

}