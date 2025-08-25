package learn_mate_it.dev.common.base

import org.springframework.http.HttpStatus

interface BaseStatus {
    val httpStatus: HttpStatus
    val code: String
    val message: String
}