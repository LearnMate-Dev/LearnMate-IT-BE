package learn_mate_it.dev.domain.auth.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.domain.auth.domain.enums.TokenType
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = getAccessToken(request)
            if (accessToken != null && jwtUtil.isTokenValid(accessToken, TokenType.ACCESS)) {
                val userId = jwtUtil.getUserIdFromAccessToken(accessToken)
                val authentication= UsernamePasswordAuthenticationToken(
                    userId, null, listOf()
                )

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            log.warn("[*] JWT filter error : ${e.message}")
            // TODO:
        }

        filterChain.doFilter(request, response)
    }

    private fun getAccessToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return null
        } else {
            return bearerToken.substring(7)
        }
    }

}