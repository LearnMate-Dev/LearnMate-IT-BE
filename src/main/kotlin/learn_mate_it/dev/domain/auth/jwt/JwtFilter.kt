package learn_mate_it.dev.domain.auth.jwt

import ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.common.base.BaseStatus
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.domain.enums.TokenType
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val mapper = ObjectMapper()

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
        } catch(e: GeneralException) {
            handleGeneralJwtError(e.errorStatus, response)
            return
        }
        catch (e: Exception) {
            log.warn("[*] JWT filter error : ${e.message}")
            handleJwtError(e.message, response)
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun getAccessToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            null
        } else {
            bearerToken.substring(7)
        }
    }

    private fun handleGeneralJwtError(errorStatus: BaseStatus, response: HttpServletResponse) {
        val errorResponse = ApiResponse.error(errorStatus).body!!
        setHttpServletResponse(errorStatus.httpStatus.value(), errorResponse, response)
    }

    private fun handleJwtError(msg: String?, response: HttpServletResponse) {
        val errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR
        val errorResponse = ApiResponse.error(errorStatus, msg ?: errorStatus.message).body!!
        setHttpServletResponse(errorStatus.httpStatus.value(), errorResponse, response)
    }

    private fun setHttpServletResponse(status: Int, errorResponse: ApiResponse<*>, response: HttpServletResponse) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = status
        response.writer.write(mapper.writeValueAsString(errorResponse))
    }

}