package learn_mate_it.dev.common.base

import org.springframework.http.HttpStatus

interface BaseErrorStatus {
    val httpStatus: HttpStatus
    val code: String
    val message: String
}