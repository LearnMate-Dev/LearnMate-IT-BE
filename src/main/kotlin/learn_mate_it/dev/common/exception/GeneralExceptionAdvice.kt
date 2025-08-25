package learn_mate_it.dev.common.exception

import ApiResponse
import learn_mate_it.dev.common.base.BaseStatus
import learn_mate_it.dev.common.status.ErrorStatus
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
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
        log.error(">>>>>>>>GeneralException: ", e)
        return ApiResponse.error(e.errorStatus)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders,
                                              status: HttpStatusCode,
                                              request: WebRequest
    ): ResponseEntity<Any>? {
        val errorMessage = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
        val body = createErrorBody(errorMessage, ErrorStatus.BAD_REQUEST)

        log.error(">>>>>>>>MethodArgumentNotValidException: ", ex)
        return handleExceptionInternal(ex, body, headers, status, request)
    }

    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointerException(e: NullPointerException): ResponseEntity<ApiResponse<Nothing>> {
        val errorMessage = "서버에서 예기치 않은 오류가 발생했습니다. 요청을 처리하는 중에 Null 값이 참조되었습니다."
        log.error(">>>>>>>>NullPointerException: ", e)
        return ApiResponse.error(ErrorStatus.INTERNAL_SERVER_ERROR, errorMessage)
    }


    override fun handleHttpRequestMethodNotSupported(ex: HttpRequestMethodNotSupportedException,
                                            headers: HttpHeaders,
                                            status: HttpStatusCode,
                                            request: WebRequest
    ): ResponseEntity<Any>? {
        val errorMessage = "지원하지 않는 HTTP 메소드 요청입니다: " + ex.method
        val body = createErrorBody(errorMessage, ErrorStatus.BAD_REQUEST)

        log.error(">>>>>>>>HttpRequestMethodNotSupportedException: ", ex)
        return handleExceptionInternal(ex, body, headers, status, request)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ApiResponse<Nothing>> {
        val errorMessage = "잘못된 요청입니다: " + e.message
        log.error(">>>>>>>>IllegalArgumentException: ", e)
        return ApiResponse.error(ErrorStatus.BAD_REQUEST, errorMessage)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error(">>>>>>>>Internal Server Error: ", e)
        return ApiResponse.error(ErrorStatus.INTERNAL_SERVER_ERROR)
    }

    private fun createErrorBody(errorMessage: String?, errorCode: BaseStatus): ApiResponse<Nothing> {
        return ApiResponse(
            isSuccess = false,
            code = errorCode.code,
            message = errorMessage?: errorCode.message,
            data = null
        )
    }

}