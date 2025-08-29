package learn_mate_it.dev.domain.user.application.service.impl

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.service.TokenService
import learn_mate_it.dev.domain.chat.application.service.ChatService
import learn_mate_it.dev.domain.course.application.service.CourseService
import learn_mate_it.dev.domain.diary.application.service.DiaryService
import learn_mate_it.dev.domain.user.application.dto.response.UserProfileDto
import learn_mate_it.dev.domain.user.application.service.UserService
import learn_mate_it.dev.domain.user.domain.model.User
import learn_mate_it.dev.domain.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val chatService: ChatService,
    private val courseService: CourseService,
    private val diaryService: DiaryService,
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
): UserService {

    /**
     * WithDraw And Delete All Data About User
     * @param userId
     */
    @Transactional
    override fun withDraw(userId: Long) {
        chatService.deleteByUserId(userId)
        courseService.deleteByUserId(userId)
        diaryService.deleteByUserId(userId)
        tokenService.deleteRefreshToken(userId)
        userRepository.deleteByUserId(userId)
    }

    /**
     * Get User Id And Name
     * @param userId
     */
    override fun getUserProfile(userId: Long): UserProfileDto {
        val user = getUser(userId)
        return UserProfileDto.toUserProfileDto(user)
    }

    private fun getUser(userId: Long): User {
        return userRepository.findByUserId(userId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_USER)
    }

}