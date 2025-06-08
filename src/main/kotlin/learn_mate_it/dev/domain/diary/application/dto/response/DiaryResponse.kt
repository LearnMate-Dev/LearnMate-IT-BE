package learn_mate_it.dev.domain.diary.application.dto.response

import learn_mate_it.dev.domain.diary.domain.model.Diary

data class DiaryAnalysisDto(
    val diaryId: Long,
    val title: String,
    val content: String
) {
    companion object {
        fun toDiaryAnalysisDto(diary: Diary): DiaryAnalysisDto {
            return DiaryAnalysisDto(
                diaryId = diary.diaryId,
                title = diary.getCreatedAtKoreanFormatted(),
                content = diary.content
            )
        }
    }
}