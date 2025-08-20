package learn_mate_it.dev.domain.diary.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class SpellingCategory(
    val title: String,
    val weight: Double
) {

    GRAMMAR("문법 오류", 1.5), WORD("단어 규칙 위반", 1.0), SPACING("띄어쓰기", 0.8),
    STANDARD("표준어 위반", 0.8), TYPO("오탈자", 1.0), FOREIGN_WORD("외래어 표기법", 0.5),
    SENTENCE("문장 오류", 1.3), ETC("기타", 1.0);

    companion object {
        fun from(category: String): SpellingCategory {
            return entries.find { it.name == category }
            ?: throw GeneralException(ErrorStatus.INVALID_SPELLING_CATEGORY)
        }
    }

}