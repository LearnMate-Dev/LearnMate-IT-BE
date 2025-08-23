package learn_mate_it.dev.domain.auth.application.service

import learn_mate_it.dev.domain.auth.application.dto.request.AppleLoginRequest
import learn_mate_it.dev.domain.auth.application.dto.request.SignInRequest
import learn_mate_it.dev.domain.auth.application.dto.request.SignUpRequest
import learn_mate_it.dev.domain.auth.application.dto.response.TokenResponse
import org.springframework.security.core.Authentication


interface AuthService {

    fun signUp(request: SignUpRequest)
    fun signIn(request: SignInRequest): TokenResponse
    fun authenticateWithApple(request: AppleLoginRequest): Authentication

}