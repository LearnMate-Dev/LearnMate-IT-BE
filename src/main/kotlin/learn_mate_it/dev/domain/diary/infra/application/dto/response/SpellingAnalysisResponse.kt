package learn_mate_it.dev.domain.diary.infra.application.dto.response

data class SpellingAnalysisResponse(
    val origin: String,
    val revised: String,
    val revisedSentences: List<RevisedSentence>?,
    val language: String
)

data class RevisedSentence(
    val origin: String,
    val revised: String,
    val revisedBlocks: List<RevisedBlock>?,
)

data class RevisedBlock(
    val origin: Origin,
    val revised: String,
    val revisions: List<Revision>,
)

data class Origin(
    val content: String,
    val beginOffset: Int,
    val length: Int
)

data class Revision(
    val revised: String,
    val category: String,
    val comment: String,
    val examples: List<String>,
    val ruleArticle: String,
    val score: Int
)