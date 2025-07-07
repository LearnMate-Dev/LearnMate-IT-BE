package learn_mate_it.dev.domain.user.application.service.impl

import jakarta.transaction.Transactional
import learn_mate_it.dev.domain.auth.application.service.AuthService
import learn_mate_it.dev.domain.chat.application.service.ChatService
import learn_mate_it.dev.domain.course.application.service.CourseService
import learn_mate_it.dev.domain.diary.application.service.DiaryService
import learn_mate_it.dev.domain.user.application.service.UserService
import learn_mate_it.dev.domain.user.domain.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val chatService: ChatService,
    private val courseService: CourseService,
    private val diaryService: DiaryService,
    private val authService: AuthService,
    private val userRepository: UserRepository,
): UserService {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Logout And Delete Refresh Token
     * @param refreshToken from Request Header
     */
    override fun logout(refreshToken: String) {
        authService.deleteRefreshToken(refreshToken)
    }


    /**
     * WithDraw And Delete All Data About User
     * @param userId
     */
    @Transactional
    override fun withDraw(userId: Long) {
        chatService.deleteByUserId(userId)
        courseService.deleteByUserId(userId)
        diaryService.deleteByUserId(userId)
        authService.deleteRefreshToken(userId)
        userRepository.deleteByUserId(userId)
    }

}