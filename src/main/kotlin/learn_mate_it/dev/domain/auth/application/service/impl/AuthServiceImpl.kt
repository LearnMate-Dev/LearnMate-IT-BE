package learn_mate_it.dev.domain.auth.application.service.impl

import io.jsonwebtoken.Claims
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.dto.request.AppleLoginRequest
import learn_mate_it.dev.domain.auth.application.service.AppleClient
import learn_mate_it.dev.domain.auth.application.service.AuthService
import learn_mate_it.dev.domain.auth.domain.enums.TokenType
import learn_mate_it.dev.domain.auth.domain.repository.RefreshTokenRepository
import learn_mate_it.dev.domain.auth.infra.application.dto.response.Key
import learn_mate_it.dev.domain.auth.jwt.JwtUtil
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Service
class AuthServiceImpl(
    private val jwtUtil: JwtUtil,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val appleClient: AppleClient
): AuthService {

    override fun authenticateWithApple(request: AppleLoginRequest): Authentication {
        val claims = validateAppleToken(request.identityToken)

        val attributes = claims.toMutableMap()
        request.username?.let {
            attributes["username"] = it
        }

        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
        val principal = DefaultOAuth2User(authorities, attributes, "sub")

        return OAuth2AuthenticationToken(
            principal,
            authorities,
            "apple"
        )
    }

    /**
     * Validate Apple's Identify Token And Get Claims
     */
    private fun validateAppleToken(identityToken: String): Claims {
        val pubKeyResponse = appleClient.getPublicKey()
        val kid = jwtUtil.getKidFromToken(identityToken)

        val matchedPubKey = getMatchedPublicKey(kid, pubKeyResponse.keys)
        val pubKey = createPublicKey(matchedPubKey)

        return jwtUtil.getClaimFromIdentityTokenWithPubKey(identityToken, pubKey)
    }

    private fun getMatchedPublicKey(kid: String, pubKeyList: List<Key>): Key {
        return pubKeyList.firstOrNull { it.kid == kid}
            ?: throw GeneralException(ErrorStatus.APPLE_LOGIN_NO_MATCHING_PUB_KEY)
    }

    private fun createPublicKey(matchedPubKey: Key): PublicKey {
        val n = BigInteger(1, Base64.getUrlDecoder().decode(matchedPubKey.n))
        val e = BigInteger(1, Base64.getUrlDecoder().decode(matchedPubKey.e))

        val keySpec = RSAPublicKeySpec(n, e)
        val keyFactory = KeyFactory.getInstance(matchedPubKey.kty)
        return keyFactory.generatePublic(keySpec)
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

    private fun validRefreshToken(refreshToken: String) {
        if (!jwtUtil.isTokenValid(refreshToken, TokenType.REFRESH)) {
            throw GeneralException(TokenType.REFRESH.errorStatus)
        }
    }

}