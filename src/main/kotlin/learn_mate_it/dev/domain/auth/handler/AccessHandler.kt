package learn_mate_it.dev.domain.auth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.common.base.BaseStatus
import learn_mate_it.dev.common.status.ErrorStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class AuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?,
    ) {
        val errorStatus: ErrorStatus = ErrorStatus.UNAUTHORIZED
        setHttpResponse(errorStatus, response)
    }
}

@Component
class AccessDeniedHandler: AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        val errorStatus: ErrorStatus = ErrorStatus.FORBIDDEN
        setHttpResponse(errorStatus, response)
    }
}

private fun setHttpResponse(errorStatus: BaseStatus, response: HttpServletResponse) {
    val mapper = ObjectMapper()
    response.contentType = MediaType.APPLICATION_JSON_VALUE
    response.characterEncoding = "UTF-8"
    response.status = errorStatus.httpStatus.value()
    val errorResponse = ApiResponse.error(errorStatus).body
    response.writer.write(mapper.writeValueAsString(errorResponse))
}