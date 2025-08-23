package learn_mate_it.dev.domain.auth.application.service.impl

import io.jsonwebtoken.Claims
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.dto.request.AppleLoginRequest
import learn_mate_it.dev.domain.auth.application.dto.request.SignInRequest
import learn_mate_it.dev.domain.auth.application.dto.request.SignUpRequest
import learn_mate_it.dev.domain.auth.application.dto.response.TokenResponse
import learn_mate_it.dev.domain.auth.application.service.AppleClient
import learn_mate_it.dev.domain.auth.application.service.AuthService
import learn_mate_it.dev.domain.auth.application.service.TokenService
import learn_mate_it.dev.domain.auth.infra.application.dto.response.Key
import learn_mate_it.dev.domain.auth.jwt.JwtUtil
import learn_mate_it.dev.domain.user.domain.enums.PROVIDER
import learn_mate_it.dev.domain.user.domain.model.User
import learn_mate_it.dev.domain.user.domain.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
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
    private val appleClient: AppleClient,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
    private val jwtUtil: JwtUtil
): AuthService {

    /**
     * Sign-Up with Email, Username, Pwd
     */
    override fun signUp(request: SignUpRequest) {
        checkEmailExist(request.email)
        checkPwdPatternIsValid(request.password)

        userRepository.save(
            User(
                username = request.username,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                provider = PROVIDER.LOCAL
            )
        )
    }

    private fun checkEmailExist(email: String) {
        if (userRepository.existsByEmail(email)) {
            throw GeneralException(ErrorStatus.ALREADY_ACCOUNT_EXIST)
        }
    }

    private fun checkPwdPatternIsValid(pwd: String) {
        val regex = Regex("^(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?]).{8,}\$")
        if (!pwd.matches(regex)) {
            throw GeneralException(ErrorStatus.INVALID_PASSWORD_FORMAT)
        }
    }

    /**
     * Sign-In with Email, Pwd
     */
    override fun signIn(request: SignInRequest): TokenResponse {
        val user = getUserByEmail(request.email)
        checkPwdIsMatch(request.password, user.password!!)

        val (accessToken, refreshToken) = tokenService.createAndSaveToken(user.userId)
        return TokenResponse(accessToken, refreshToken)
    }

    private fun getUserByEmail(email: String): User {
        val user = userRepository.findByEmail(email)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_USER)

        if (user.provider != PROVIDER.LOCAL) {
            throw GeneralException(ErrorStatus.SOCIAL_LOGIN_USER)
        }
        return user
    }

    private fun checkPwdIsMatch(rawPassword: String, encodedPwd: String) {
        if (!passwordEncoder.matches(rawPassword, encodedPwd)) {
            throw GeneralException(ErrorStatus.INVALID_PASSWORD)
        }
    }

    /**
     * Valid Apple's Identity Token And Handle Social Sign-Up
     */
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

}