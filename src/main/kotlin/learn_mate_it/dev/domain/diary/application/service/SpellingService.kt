package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse


interface SpellingService {

    fun saveSpellingAndRevisions(diary: Diary, spellingAnalysisResponse: SpellingAnalysisResponse?): Pair<Spelling?, List<SpellingRevision>?>

    fun deleteByUserId(userId: Long)
    fun deleteByDiaryId(diaryId: Long)

}