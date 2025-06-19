package learn_mate_it.dev.domain.auth.application.service

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.domain.enums.TokenType
import learn_mate_it.dev.domain.auth.jwt.JwtUtil
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val jwtUtil: JwtUtil
): AuthService {

    /**
     * Reissue AccessToken
     */
    override fun reissueToken(refreshToken: String): String {
        val newRefreshToken = getRefreshToken(refreshToken)
        validRefreshToken(newRefreshToken)

        val userId = jwtUtil.getUserIdFromRefreshToken(newRefreshToken)
        return jwtUtil.createAccessToken(userId)
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