package learn_mate_it.dev.domain.auth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.common.base.BaseErrorStatus
import learn_mate_it.dev.common.status.ErrorStatus
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class OAuthLoginFailureHandler: AuthenticationFailureHandler {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {

        log.warn("[*] Login Fail : ${exception?.stackTraceToString()}")
        val errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR
        setHttpResponse(errorStatus, response)
    }

    private fun setHttpResponse(errorStatus: BaseErrorStatus, response: HttpServletResponse) {
        val mapper = ObjectMapper()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = errorStatus.httpStatus.value()
        val errorResponse = ApiResponse.error(errorStatus).body
        response.writer.write(mapper.writeValueAsString(errorResponse))
    }

}