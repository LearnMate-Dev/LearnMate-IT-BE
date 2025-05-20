package learn_mate_it.dev.common.status

import learn_mate_it.dev.common.base.BaseErrorStatus
import org.springframework.http.HttpStatus

enum class ErrorStatus (
    override val httpStatus: HttpStatus,
    override val code: String,
    override val message: String
) : BaseErrorStatus {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404", "요청한 자원을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "405", "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 내부 오류입니다.")

}