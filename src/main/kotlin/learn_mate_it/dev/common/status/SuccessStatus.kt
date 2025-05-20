package learn_mate_it.dev.common.status

import learn_mate_it.dev.common.base.BaseSuccessStatus
import org.springframework.http.HttpStatus

enum class SuccessStatus (
    override val httpStatus: HttpStatus,
    override val code: String,
    override val message: String
) : BaseSuccessStatus {

    OK(HttpStatus.OK, "200", "요청이 성공적으로 처리되었습니다."),

}