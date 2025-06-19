package learn_mate_it.dev.common.exception

import ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GeneralExceptionAdvice : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(GeneralException::class)
    fun handleGeneralException(e: GeneralException): ResponseEntity<ApiResponse<Nothing>> {
        log.warn(">>>>>>>>GeneralException: ${e.message}")
        return ApiResponse.error(e.errorStatus)
    }

}