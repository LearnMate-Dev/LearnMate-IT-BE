package learn_mate_it.dev.domain.auth.presentation

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.domain.auth.application.dto.request.AppleLoginRequest
import learn_mate_it.dev.domain.auth.application.service.AuthService
import learn_mate_it.dev.domain.auth.handler.OAuthLoginSuccessHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val oAuthLoginSuccessHandler: OAuthLoginSuccessHandler
) {

    @PostMapping("/apple/login")
    fun appleLogin(
        @RequestBody request: AppleLoginRequest,
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ) {
        val authentication = authService.authenticateWithApple(request)
        oAuthLoginSuccessHandler.onAuthenticationSuccess(httpRequest, httpResponse, authentication)
    }

    @PostMapping("/reissue")
    fun reissueToken(
        @RequestHeader("Authorization") refreshToken: String,
        response: HttpServletResponse
    ) {
        val newAccessToken = authService.reissueToken(refreshToken)
        val redirectUri = "com.learnmate.app://oauth2/callback?accessToken=$newAccessToken"
        response.sendRedirect(redirectUri)
    }

}