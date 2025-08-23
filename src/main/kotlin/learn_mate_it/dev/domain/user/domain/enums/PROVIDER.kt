package learn_mate_it.dev.domain.user.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class PROVIDER {
    GOOGLE, APPLE, LOCAL;

    companion object {
        fun from(name: String) : PROVIDER {
            return entries.find { it.name.equals(name) }
                ?: throw GeneralException(ErrorStatus.INVALID_OAUTH_PROVIDER)
        }
    }
}