package learn_mate_it.dev.domain.auth.application.service.impl

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.service.AuthService
import learn_mate_it.dev.domain.auth.domain.enums.TokenType
import learn_mate_it.dev.domain.auth.domain.repository.RefreshTokenRepository
import learn_mate_it.dev.domain.auth.jwt.JwtUtil
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val jwtUtil: JwtUtil,
    private val refreshTokenRepository: RefreshTokenRepository,
): AuthService {

    /**
     * Reissue AccessToken
     */
    override fun reissueToken(refreshToken: String): String {
        val cleanRefreshToken = getRefreshToken(refreshToken)
        validRefreshToken(cleanRefreshToken)

        val userId = jwtUtil.getUserIdFromRefreshToken(cleanRefreshToken)
        return jwtUtil.createAccessToken(userId)
    }

    override fun deleteRefreshToken(userId: Long) {
        refreshTokenRepository.deleteAll(refreshTokenRepository.findByUserId(userId))
    }

    override fun deleteRefreshToken(refreshToken: String) {
        val cleanRefreshToken = getRefreshToken(refreshToken)
        refreshTokenRepository.findByRefreshToken(cleanRefreshToken)?.apply {
            refreshTokenRepository.delete(this)
        }
    }

    private fun getRefreshToken(token: String): String {
        if (!token.startsWith("Bearer ")) {
            throw GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN)
        } else {
           return token.substring(7)
        }
    }

    private fun validRefreshToken(refreshToken: String) {
        if (!jwtUtil.isTokenValid(refreshToken, TokenType.REFRESH)) {
            throw GeneralException(TokenType.REFRESH.errorStatus)
        }
    }

}