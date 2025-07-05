package learn_mate_it.dev.domain.diary.application.dto.response

import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingFeedback
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision

data class DiaryDto(
    val diaryId: Long,
    val createdAt: String,
    val title: String,
    val originContent: String,
    val spellingDto: SpellingDto,
    val feedback: String?
) {
    companion object {
        fun toDiaryDto(
            diary: Diary,
            spelling: Spelling?,
            revisions: List<SpellingRevision>?,
            feedback: SpellingFeedback?
        ): DiaryDto {
            return DiaryDto(
                diaryId = diary.diaryId,
                createdAt = diary.getCreatedAtKoreanFormatted(),
                title = diary.getCreatedAtKoreanFormatted(),
                originContent = diary.content,
                spellingDto = SpellingDto.toSpellingDto(spelling, revisions),
                feedback = feedback?.content
            )
        }
    }
}