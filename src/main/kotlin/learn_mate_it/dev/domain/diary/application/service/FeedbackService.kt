package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.domain.model.Diary

interface FeedbackService {

    fun analysisFeedback(diary: Diary, content: String): String

    fun deleteByUserId(userId: Long)
    fun deleteByDiaryId(diaryId: Long)

}