package learn_mate_it.dev.domain.auth.application.service

import org.springframework.security.core.Authentication


interface AuthService {

    fun authenticateWithApple(identityToken: String): Authentication
    fun reissueToken(refreshToken: String): String
    fun deleteRefreshToken(userId: Long)
    fun deleteRefreshToken(refreshToken: String)

}