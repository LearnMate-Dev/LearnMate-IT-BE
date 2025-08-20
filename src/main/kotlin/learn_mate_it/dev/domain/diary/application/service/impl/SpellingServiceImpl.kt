package learn_mate_it.dev.domain.diary.application.service.impl

import jakarta.transaction.Transactional
import learn_mate_it.dev.domain.diary.application.service.SpellingService
import learn_mate_it.dev.domain.diary.domain.enums.SpellingCategory
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision
import learn_mate_it.dev.domain.diary.domain.repository.SpellingRepository
import learn_mate_it.dev.domain.diary.domain.repository.SpellingRevisionRepository
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.springframework.stereotype.Service

@Service
class SpellingServiceImpl(
    private val spellingRepository: SpellingRepository,
    private val spellingRevisionRepository: SpellingRevisionRepository,
) : SpellingService {

    @Transactional
    override fun saveSpellingAndRevisions(diary: Diary, spellingAnalysisResponse: SpellingAnalysisResponse?): Pair<Spelling?, List<SpellingRevision>?> {
        // get score of spelling
        val score = getSpellingScore(spellingAnalysisResponse)

        // save spelling entity
        val spelling = spellingRepository.save(
            Spelling(
                revisedContent = spellingAnalysisResponse?.revised,
                score = score,
                diary = diary
            )
        )

        // save separate spelling analysis
        val revisions = spellingAnalysisResponse?.revisedSentences
            ?.flatMap { it.revisedBlocks.orEmpty() }
            ?.flatMap { block ->
                block.revisions.map { revision ->
                    SpellingRevision(
                        originContent = block.origin.content,
                        beginOffset = block.origin.beginOffset,
                        revisedContent = revision.revised,
                        examples = revision.examples.toTypedArray(),
                        comment = revision.comment,
                        category = SpellingCategory.from(revision.category),
                        spelling = spelling
                    )
                }
            }.orEmpty()
        spellingRevisionRepository.saveAll(revisions)

        return Pair(spelling, revisions)
    }

    private fun getSpellingScore(analysisResponse: SpellingAnalysisResponse?): Int {
        if (analysisResponse?.revisedSentences!!.isEmpty()) return 100

        var totalWeight = 0.0
        analysisResponse.revisedSentences.forEach { sentence ->
            sentence.revisedBlocks.orEmpty().forEach { block ->
                block.revisions.forEach { revision ->
                    val weight = SpellingCategory.from(revision.category).weight
                    totalWeight += weight
                }
            }
        }

        val deductionPerRevision = 5.0
        val rawScore = 100.0 - totalWeight * deductionPerRevision
        return rawScore.coerceIn(0.0, 100.0).toInt()
    }

    @Transactional
    override fun deleteByUserId(userId: Long) {
        spellingRevisionRepository.deleteByUserId(userId)
        spellingRepository.deleteByUserId(userId)
    }

    @Transactional
    override fun deleteByDiaryId(diaryId: Long) {
        spellingRevisionRepository.deleteByDiaryId(diaryId)
        spellingRepository.deleteByDiaryId(diaryId)
    }

}