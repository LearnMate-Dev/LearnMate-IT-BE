package learn_mate_it.dev.domain.auth.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.domain.enums.TokenType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token.expiration-time}") private val accessTokenExpirationTime: Long,
    @Value("\${jwt.refresh-token.expiration-time}") private val refreshTokenExpirationTime: Long,
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private fun getSigningKey() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    /**
     * Generate Token
     */
    fun createAccessToken(userId: Long) = createToken(userId, accessTokenExpirationTime)
    fun createRefreshToken(userId: Long) = createToken(userId, refreshTokenExpirationTime)

    private fun createToken(userId: Long, expirationTime: Long): String {
        return Jwts.builder()
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .claim("userId", userId.toString())
            .signWith(getSigningKey())
            .compact()
    }

    /**
     * Validation of Token
     */
    fun isTokenValid(token: String, tokenType: TokenType): Boolean {
        try {
            val value = getClaimFromToken(token, tokenType)
            if (value == null) {
                throw GeneralException(tokenType.errorStatus)
            }
            return true
        } catch (e: ExpiredJwtException) {
            log.warn("[*] JWT Expired token error : ${e.message}")
            throw GeneralException(ErrorStatus.EXPIRED_TOKEN_ERROR)
        }
        catch (e: Exception) {
            log.warn("[*] JWT validation error: ${e.message}")
            throw GeneralException(tokenType.errorStatus)
        }
    }

    /**
     * Get Info From Token
     */
    fun getUserIdFromAccessToken(token: String) = getClaimFromToken(token, TokenType.ACCESS)
    fun getUserIdFromRefreshToken(token: String) = getClaimFromToken(token, TokenType.REFRESH)

    private fun getClaimFromToken(token: String, tokenType: TokenType): Long {
        try {
            return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .payload
                .get(tokenType.claimKey, String::class.java).toLong()
        } catch (e: Exception) {
            log.warn("[*] JWT token error : ${e.message}")
            throw GeneralException(tokenType.errorStatus)
        }
    }

}