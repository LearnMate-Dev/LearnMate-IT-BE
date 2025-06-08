package learn_mate_it.dev.domain.diary.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.dto.request.PostDiaryDto
import learn_mate_it.dev.domain.diary.application.dto.response.DiaryAnalysisDto
import learn_mate_it.dev.domain.diary.domain.model.Diary
import learn_mate_it.dev.domain.diary.domain.repository.DiaryRepository
import learn_mate_it.dev.domain.user.domain.model.User
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository
) : DiaryService {

    private final val CONTENT_LENGTH: Int = 500

    @Transactional
    override fun postAndAnalysisDiary(diaryRequest: PostDiaryDto): DiaryAnalysisDto {
        val user = getUser()

        validNotWrittenToday(user.userId)
        validStringLength(diaryRequest.content, CONTENT_LENGTH, ErrorStatus.DIARY_CONTENT_OVER_FLOW)

        // save diary
        val diary = diaryRepository.save(
            Diary(
                content = diaryRequest.content,
                userId = user.userId,
            )
        )

        // get spelling feedback from api


        return DiaryAnalysisDto.toDiaryAnalysisDto(diary)
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

    private fun getUser(): User {
        // TODO: get user info
        return User(username = "username")
    }

}