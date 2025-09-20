package learn_mate_it.dev.domain.diary.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class SpellingCategory(
    val title: String,
    val weight: Double
) {

    GRAMMAR("맞춤법", 1.5), SPACING("띄어쓰기", 0.8),
    STANDARD("표준어 위반", 0.8), ETC("기타", 1.0);

    companion object {
        fun from(category: String): SpellingCategory {
            return entries.find { it.name == category }
            ?: throw GeneralException(ErrorStatus.INVALID_SPELLING_CATEGORY)
        }
    }

}