package learn_mate_it.dev.domain.auth.domain

import learn_mate_it.dev.common.status.ErrorStatus

enum class TokenType(
    val claimKey: String,
    val errorStatus: ErrorStatus,
) {

    ACCESS("userId", ErrorStatus.INVALID_ACCESS_TOKEN),
    REFRESH("userId", ErrorStatus.INVALID_REFRESH_TOKEN)

}