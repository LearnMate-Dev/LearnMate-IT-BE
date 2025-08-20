package learn_mate_it.dev.domain.diary.application.dto.response

import learn_mate_it.dev.domain.diary.domain.model.Spelling
import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision

data class SpellingDto(
    val revisedContent: String?,
    val score: Int,
    val revisions: List<SpellingRevisionDto>?
) {
    companion object {
        fun toSpellingDto(
            spelling: Spelling?,
            revisions: List<SpellingRevision>?
        ): SpellingDto {
            return SpellingDto(
                revisedContent = spelling?.revisedContent,
                score = spelling?.score ?: 100,
                revisions = revisions?.map { SpellingRevisionDto.toSpellingRevisionDto(it) }
            )
        }
    }
}

data class SpellingRevisionDto(
    val originContent: String,
    val revisedContent: String,
    val beginOffset: Int,
    val comment: String,
    val category: String,
    val examples: List<String>?
) {
    companion object {
        fun toSpellingRevisionDto(
            revision: SpellingRevision
        ): SpellingRevisionDto {
            return SpellingRevisionDto(
                originContent = revision.originContent,
                revisedContent = revision.revisedContent,
                beginOffset = revision.beginOffset,
                comment = revision.comment,
                category = revision.category.title,
                examples = revision.examples?.toList()
            )
        }
    }
}