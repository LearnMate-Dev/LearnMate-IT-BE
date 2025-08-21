package learn_mate_it.dev.domain.auth.application.service

import learn_mate_it.dev.domain.auth.application.dto.request.AppleLoginRequest
import org.springframework.security.core.Authentication


interface AuthService {

    fun authenticateWithApple(request: AppleLoginRequest): Authentication
    fun reissueToken(refreshToken: String): String
    fun deleteRefreshToken(userId: Long)
    fun deleteRefreshToken(refreshToken: String)

}