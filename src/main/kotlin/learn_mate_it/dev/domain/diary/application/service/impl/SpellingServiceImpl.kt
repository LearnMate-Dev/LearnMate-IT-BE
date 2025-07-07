package learn_mate_it.dev.domain.diary.application.service.impl

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import learn_mate_it.dev.domain.diary.application.service.SpellingService
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
    private val entityManager: EntityManager
) : SpellingService {

    @Transactional
    override fun saveSpellingAndRevisions(diary: Diary, spellingAnalysisResponse: SpellingAnalysisResponse): Spelling {
        // get score of spelling
        val score = getSpellingScore(spellingAnalysisResponse)

        // save spelling entity
        val spelling = spellingRepository.save(
            Spelling(
                revisedContent = spellingAnalysisResponse.revised,
                score = score,
                diary = diary
            )
        )

        // save separate spelling analysis
        val revisions = spellingAnalysisResponse.revisedSentences
            ?.flatMap { it.revisedBlocks.orEmpty() }
            ?.flatMap { block ->
                block.revisions.map { revision ->
                    SpellingRevision(
                        originContent = block.origin.content,
                        beginOffset = block.origin.beginOffset,
                        revisedContent = revision.revised,
                        examples = revision.examples.toTypedArray(),
                        comment = revision.comment,
                        spelling = spelling
                    )
                }
            }.orEmpty()
        spellingRevisionRepository.saveAll(revisions)
        entityManager.flush()

        return spelling
    }

    private fun getSpellingScore(analysisResponse: SpellingAnalysisResponse): Int {
        val sentences = analysisResponse.revisedSentences ?: return 100
        // TODO: feature score
        return 0
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