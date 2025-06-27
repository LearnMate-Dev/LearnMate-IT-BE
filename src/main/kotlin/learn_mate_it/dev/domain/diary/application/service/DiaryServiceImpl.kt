package learn_mate_it.dev.domain.diary.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryAnalysisDto
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision
import learn_mate_it.dev.domain.diary.domain.repository.DiaryRepository
import learn_mate_it.dev.domain.diary.domain.repository.SpellingFeedbackRepository
import learn_mate_it.dev.domain.diary.domain.repository.SpellingRepository
import learn_mate_it.dev.domain.diary.domain.repository.SpellingRevisionRepository
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val spellingRepository: SpellingRepository,
    private val spellingRevisionRepository: SpellingRevisionRepository,
    private val spellingFeedbackRepository: SpellingFeedbackRepository,
    private val spellingService: SpellingService,
) : DiaryService {

    private final val CONTENT_LENGTH: Int = 500

    /**
     * Post Diary And Analysis Diary's Spelling And Get Feedback From AI About Spelling
     * Diary's Title will be the date
     *
     * @param diaryRequest content of diary
     * @return DiaryAnalysisDto content of diary and analysis about diary (score, spelling comment, examples)
     */
    @Transactional
    override fun postAndAnalysisDiary(userId: Long, diaryRequest: PostDiaryDto): DiaryAnalysisDto {
        validNotWrittenToday(userId)
        validStringLength(diaryRequest.content, CONTENT_LENGTH, ErrorStatus.DIARY_CONTENT_OVER_FLOW)

        // save diary
        val diary = diaryRepository.save(
            Diary(
                content = diaryRequest.content,
                userId = userId,
            )
        )

        // get spelling analysis from api
        val spellingAnalysisResponse = spellingService.analysisSpelling(diary.content)
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

        return DiaryAnalysisDto.toDiaryAnalysisDto(diary, spelling, revisions)
    }

    /**
     * Delete Diary By DiaryId
     *
     * @param diaryId
     */
    @Transactional
    override fun deleteDiary(userId: Long, diaryId: Long) {
        val diary = getDiary(diaryId)
        validIsUserAuthorizedForDiary(userId, diary)

        spellingRevisionRepository.deleteByDiaryId(diaryId)
        spellingFeedbackRepository.deleteByDiaryId(diaryId)
        spellingRepository.deleteByDiaryId(diaryId)
        diaryRepository.delete(diary)
    }

    fun validIsUserAuthorizedForDiary(userId: Long, diary: Diary) {
        if (diary.userId != userId) {
            throw GeneralException(ErrorStatus.FORBIDDEN_FOR_DIARY)
        }
    }

    @Transactional
    override fun deleteByUserId(userId: Long) {
        spellingRevisionRepository.deleteByUserId(userId)
        spellingFeedbackRepository.deleteByUserId(userId)
        spellingRepository.deleteByUserId(userId)
        diaryRepository.deleteByUserId(userId)
    }

    private fun getSpellingScore(analysisResponse: SpellingAnalysisResponse): Int {
        val sentences = analysisResponse.revisedSentences ?: return 100
        // TODO: feature score
        return 0
    }

    private fun validNotWrittenToday(userId: Long) {
        val startDay = LocalDate.now().atStartOfDay()
        val endDay = startDay.plusDays(1)

        val isWrittenToday = diaryRepository.existsByUserIdAndCreatedAt(userId, startDay, endDay)
        require(!isWrittenToday) { throw GeneralException(ErrorStatus.ALREADY_DIARY_WRITTEN)}
    }

    private fun validStringLength(content: String, length: Int, errorStatus: ErrorStatus) {
        require(content.length <= length) { throw GeneralException(errorStatus) }
    }

    private fun getDiaryFetchSpelling(userId: Long, diaryId: Long): Diary {
        return diaryRepository.findByUserIdAndDiaryId(userId, diaryId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_DIARY)
    }

    private fun getDiary(diaryId: Long): Diary {
        return diaryRepository.findByDiaryId(diaryId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_DIARY)
    }

}