package learn_mate_it.dev.common.exception

import learn_mate_it.dev.common.base.BaseStatus

class GeneralException(
    val errorStatus: BaseStatus
) : RuntimeException(errorStatus.message)