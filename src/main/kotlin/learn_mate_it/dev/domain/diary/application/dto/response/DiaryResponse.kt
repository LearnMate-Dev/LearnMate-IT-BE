package learn_mate_it.dev.domain.diary.application.dto.response

import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision

data class DiaryAnalysisDto(
    val diaryId: Long,
    val title: String,
    val originContent: String,
    val spellingDto: SpellingDto
) {
    companion object {
        fun toDiaryAnalysisDto(
            diary: Diary,
            spelling: Spelling,
            revisions: List<SpellingRevision>?
        ): DiaryAnalysisDto {
            return DiaryAnalysisDto(
                diaryId = diary.diaryId,
                title = diary.getCreatedAtKoreanFormatted(),
                originContent = diary.content,
                spellingDto = SpellingDto.toSpellingDto(spelling, revisions)
            )
        }
    }
}