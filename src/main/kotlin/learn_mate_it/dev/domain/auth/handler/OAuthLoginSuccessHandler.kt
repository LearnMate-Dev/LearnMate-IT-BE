package learn_mate_it.dev.domain.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.service.TokenService
import learn_mate_it.dev.domain.auth.domain.dto.AppleUserInfo
import learn_mate_it.dev.domain.auth.domain.dto.GoogleUserInfo
import learn_mate_it.dev.domain.auth.domain.dto.OAuth2UserInfo
import learn_mate_it.dev.domain.user.domain.enums.PROVIDER
import learn_mate_it.dev.domain.user.domain.model.User
import learn_mate_it.dev.domain.user.domain.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuthLoginSuccessHandler(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
): AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        // extract user's oauth information
        val oAuthToken = authentication as OAuth2AuthenticationToken
        val userInfo = getOAuth2UserInfo(oAuthToken)

        // save new user or deal with existing user
        val user = userRepository.findByProviderId(userInfo.getProviderId())
            ?: userRepository.save(
                User(
                    username = userInfo.getName(),
                    providerId = userInfo.getProviderId(),
                    email = userInfo.getEmail(),
                    provider = PROVIDER.from(userInfo.getProvider())
                )
            )

        // create access, refresh token
        val (accessToken, refreshToken) = tokenService.createAndSaveToken(user.userId)

        // set response
        val redirectUri = "com.learnmate.app://oauth2/callback?accessToken=$accessToken&refreshToken=$refreshToken"
        response?.sendRedirect(redirectUri)
    }

    private fun getOAuth2UserInfo(oAuthToken: OAuth2AuthenticationToken) : OAuth2UserInfo {
        val provider = oAuthToken.authorizedClientRegistrationId
        val principal = oAuthToken.principal

        return when(provider) {
            "google" -> GoogleUserInfo(principal.attributes)
            "apple" -> AppleUserInfo(principal.attributes)
            else -> throw GeneralException(ErrorStatus.INVALID_OAUTH_PROVIDER)
        }
    }

}