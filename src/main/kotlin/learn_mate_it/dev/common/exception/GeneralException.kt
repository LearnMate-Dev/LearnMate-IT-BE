package learn_mate_it.dev.common.exception

import learn_mate_it.dev.common.base.BaseErrorStatus

class GeneralException(
    val errorStatus: BaseErrorStatus
) : RuntimeException(errorStatus.message)