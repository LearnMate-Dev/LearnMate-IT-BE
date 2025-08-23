package learn_mate_it.dev.common.exception

import ApiResponse
import learn_mate_it.dev.common.status.ErrorStatus
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GeneralExceptionAdvice : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(GeneralException::class)
    fun handleGeneralException(e: GeneralException): ResponseEntity<ApiResponse<Nothing>> {
        log.warn(">>>>>>>>GeneralException: ${e.message}")
        return ApiResponse.error(e.errorStatus)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorCode = ErrorStatus.BAD_REQUEST
        val errorMessage = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage

        val body = ApiResponse(
            isSuccess = false,
            code = errorCode.code,
            message = errorMessage?: errorCode.message,
            data = null
        )
        return handleExceptionInternal(ex, body, headers, status, request)
    }

}