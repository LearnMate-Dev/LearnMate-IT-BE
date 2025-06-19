package learn_mate_it.dev.domain.user.application.service

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.domain.repository.RefreshTokenRepository
import learn_mate_it.dev.domain.user.domain.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
): UserService {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Logout And Delete Refresh Token
     * @param refreshToken from Request Header
     */
    override fun logout(refreshToken: String) {
        val cleanRefreshToken = getRefreshToken(refreshToken)
        deleteRefreshToken(cleanRefreshToken)
    }

    private fun deleteRefreshToken(refreshToken: String) {
        refreshTokenRepository.findByRefreshToken(refreshToken)?.apply {
            log.info(this.refreshToken)
            refreshTokenRepository.delete(this)
        }
    }

    override fun withDraw() {
        TODO("Not yet implemented")
    }

    private fun getRefreshToken(token: String): String {
        if (!token.startsWith("Bearer ")) {
            throw GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN)
        } else {
            return token.substring(7)
        }
    }

}