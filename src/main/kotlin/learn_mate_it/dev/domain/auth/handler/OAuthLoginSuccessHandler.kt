package learn_mate_it.dev.domain.auth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.auth.domain.dto.GoogleUserInfo
import learn_mate_it.dev.domain.auth.domain.dto.OAuth2UserInfo
import learn_mate_it.dev.domain.auth.domain.model.RefreshToken
import learn_mate_it.dev.domain.auth.domain.repository.RefreshTokenRepository
import learn_mate_it.dev.domain.auth.jwt.JwtUtil
import learn_mate_it.dev.domain.user.domain.enums.PROVIDER
import learn_mate_it.dev.domain.user.domain.model.User
import learn_mate_it.dev.domain.user.domain.repository.UserRepository
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuthLoginSuccessHandler(
    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
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
                    provider = PROVIDER.from(userInfo.getProvider())
                )
            )

        // make new token
        val accessToken = jwtUtil.createAccessToken(user.userId)
        val refreshToken = jwtUtil.createRefreshToken(user.userId)
        saveRefreshToken(refreshToken, user.userId)

        // set response
        response!!.setHeader("accessToken", accessToken)
        setHttpResponse(response)
    }

    private fun getOAuth2UserInfo(oAuthToken: OAuth2AuthenticationToken) : OAuth2UserInfo {
        val provider = oAuthToken.authorizedClientRegistrationId
        val principal = oAuthToken.principal

        when(provider) {
            "google" -> return GoogleUserInfo(principal.attributes)
            else -> throw GeneralException(ErrorStatus.INVALID_OAUTH_PROVIDER)
        }
    }

    private fun saveRefreshToken(refreshToken: String, userId: Long) {
        refreshTokenRepository.save(
            RefreshToken(refreshToken, userId)
        )
    }

    private fun setHttpResponse(response: HttpServletResponse) {
        val mapper = ObjectMapper()
        val status = SuccessStatus.SIGN_UP_SUCCESS
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = status.httpStatus.value()

        val responseBody = ApiResponse.success(status).body
        response.writer.write(mapper.writeValueAsString(responseBody))
    }
}