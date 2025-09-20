package learn_mate_it.dev.domain.diary.infra.application.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


data class SpellingAnalysisResponse @JsonCreator constructor(
    @JsonProperty("origin") val origin: String,
    @JsonProperty("revised") val revised: String,
    @JsonProperty("revisedSentences") val revisedSentences: List<RevisedSentence>?
)

data class RevisedSentence @JsonCreator constructor(
    @JsonProperty("origin") val origin: String,
    @JsonProperty("revised") val revised: String,
    @JsonProperty("revisedBlocks") val revisedBlocks: List<RevisedBlock>?,
)

data class RevisedBlock @JsonCreator constructor(
    @JsonProperty("origin") val origin: Origin,
    @JsonProperty("revised") val revised: String,
    @JsonProperty("revisions") val revisions: List<Revision>
)

data class Origin @JsonCreator constructor(
    @JsonProperty("content") val content: String,
    @JsonProperty("beginOffset") val beginOffset: Int,
    @JsonProperty("length") val length: Int
)

data class Revision @JsonCreator constructor(
    @JsonProperty("revised") val revised: String,
    @JsonProperty("category") val category: String,
    @JsonProperty("comment") val comment: String,
    @JsonProperty("examples") val examples: List<String>
)