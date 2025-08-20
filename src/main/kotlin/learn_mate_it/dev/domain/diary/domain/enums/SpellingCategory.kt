package learn_mate_it.dev.domain.diary.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class SpellingCategory(
    val title: String
) {

    GRAMMAR("문법 오류"), WORD("단어 규칙 위반"), SPACING("띄어쓰기"),
    STANDARD("표준어 위반"), TYPO("오탈자"), FOREIGN_WORD("외래어 표기법"),
    SENTENCE("문장 오류"), ETC("기타");

    companion object {
        fun from(category: String): SpellingCategory {
            return entries.find { it.name == category }
            ?: throw GeneralException(ErrorStatus.INVALID_SPELLING_CATEGORY)
        }
    }

}