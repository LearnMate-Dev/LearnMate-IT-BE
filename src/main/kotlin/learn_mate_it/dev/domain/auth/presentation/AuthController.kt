package learn_mate_it.dev.domain.auth.presentation

import ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.auth.application.dto.request.*
import learn_mate_it.dev.domain.auth.application.dto.response.TokenResponse
import learn_mate_it.dev.domain.auth.application.service.AuthService
import learn_mate_it.dev.domain.auth.application.service.EmailVerificationService
import learn_mate_it.dev.domain.auth.application.service.TokenService
import learn_mate_it.dev.domain.auth.handler.OAuthLoginSuccessHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val tokenService: TokenService,
    private val emailVerificationService: EmailVerificationService,
    private val oAuthLoginSuccessHandler: OAuthLoginSuccessHandler
) {

    @GetMapping("/discord-test")
    fun discordTest() {
        throw Exception("Discord Error Test")
    }

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
        val newAccessToken = tokenService.reissueToken(refreshToken)
        val redirectUri = "com.learnmate.app://oauth2/callback?accessToken=$newAccessToken"
        response.sendRedirect(redirectUri)
    }

    @PostMapping("/sign-up")
    fun signUp(
        @Valid @RequestBody request: SignUpRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        authService.signUp(request)
        return ApiResponse.success(SuccessStatus.SIGN_UP_SUCCESS)
    }

    @PostMapping("/sign-in")
    fun signIn(
        @Valid @RequestBody request: SignInRequest
    ): ResponseEntity<ApiResponse<TokenResponse>> {
        val response = authService.signIn(request)
        return ApiResponse.success(SuccessStatus.SIGN_IN_SUCCESS, response)
    }

    @PostMapping("/logout")
    fun logout(
        @RequestHeader("Authorization") refreshToken: String,
        response: HttpServletResponse
    ): ResponseEntity<ApiResponse<Nothing>> {
        authService.logout(refreshToken)
        return ApiResponse.success(SuccessStatus.USER_LOGOUT_SUCCESS)
    }

    @PostMapping("/email")
    fun sendEmailVerificationCode(
        @Valid @RequestBody request: EmailVerificationRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        emailVerificationService.sendVerificationCodeToEmail(request.email)
        return ApiResponse.success(SuccessStatus.SEND_EMAIL_VERIFICATION_CODE_SUCCESS)
    }

    @PostMapping("/email/confirm")
    fun confirmEmailVerificationCode(
        @Valid @RequestBody request: ConfirmEmailVerificationRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        emailVerificationService.confirmEmailVerificationCode(request.email, request.code)
        return ApiResponse.success(SuccessStatus.VERIFY_EMAIL_SUCCESS)
    }

}