package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.SpellingFeedback

interface FeedbackService {

    fun saveFeedback(diary: Diary, content: String): SpellingFeedback

    fun deleteByUserId(userId: Long)
    fun deleteByDiaryId(diaryId: Long)

}