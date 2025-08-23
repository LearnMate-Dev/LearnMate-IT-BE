package learn_mate_it.dev.domain.auth.application.service.impl

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.service.TokenService
import learn_mate_it.dev.domain.auth.domain.enums.TokenType
import learn_mate_it.dev.domain.auth.domain.model.RefreshToken
import learn_mate_it.dev.domain.auth.domain.repository.RefreshTokenRepository
import learn_mate_it.dev.domain.auth.jwt.JwtUtil
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtUtil: JwtUtil
): TokenService {

    /**
     * Create Access, Refresh Token
     */
    override fun createAndSaveToken(userId: Long): Pair<String, String> {
        refreshTokenRepository.findByUserId(userId).firstOrNull()?.let {
            refreshTokenRepository.delete(it)
        }

        val accessToken = jwtUtil.createAccessToken(userId)
        val refreshToken = jwtUtil.createRefreshToken(userId)
        saveRefreshToken(refreshToken, userId)

        return Pair(accessToken, refreshToken)
    }

    private fun saveRefreshToken(refreshToken: String, userId: Long) {
        refreshTokenRepository.save(
            RefreshToken(refreshToken, userId)
        )
    }

    /**
     * Reissue AccessToken
     */
    override fun reissueToken(refreshToken: String): String {
        val cleanRefreshToken = getRefreshToken(refreshToken)
        validRefreshToken(cleanRefreshToken)

        val userId = jwtUtil.getUserIdFromRefreshToken(cleanRefreshToken)
        return jwtUtil.createAccessToken(userId)
    }

    private fun validRefreshToken(refreshToken: String) {
        if (!jwtUtil.isTokenValid(refreshToken, TokenType.REFRESH)) {
            throw GeneralException(TokenType.REFRESH.errorStatus)
        }
    }

    /**
     * Delete RefreshToken
     */
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

}