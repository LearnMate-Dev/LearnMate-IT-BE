package learn_mate_it.dev.domain.diary.application.dto.response

import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DiaryDto(
    val diaryId: Long,
    val createdAt: String,
    val originContent: String,
    val spellingDto: SpellingDto,
    val feedback: String?
) {
    companion object {
        fun toDiaryDto(
            diary: Diary,
            spelling: Spelling?,
            revisions: List<SpellingRevision>?,
            feedback: String
        ): DiaryDto {
            return DiaryDto(
                diaryId = diary.diaryId,
                createdAt = diary.getCreatedAtKoreanFormatted(),
                originContent = diary.content,
                spellingDto = SpellingDto.toSpellingDto(spelling, revisions),
                feedback = feedback
            )
        }

        fun toDiaryDto(
            diary: Diary
        ): DiaryDto {
            return DiaryDto(
                diaryId = diary.diaryId,
                createdAt = diary.getCreatedAtKoreanFormatted(),
                originContent = diary.content,
                spellingDto = SpellingDto.toSpellingDto(diary.spelling, diary.spelling?.revisions),
                feedback = diary.spellingFeedback?.content
            )
        }
    }
}

class SimpleDiaryDto(
    val diaryId: Long,
    val createdAt: String,
    val score: Int
) {
    constructor(diaryId: Long, createdAt: LocalDateTime, score: Int) : this(
        diaryId, createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), score
    )
}

data class DiaryCalendarDto(
    val year: Int,
    val month: Int,
    val diaryList: List<SimpleDiaryDto>
)