package learn_mate_it.dev.domain.diary.infra.application.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SpellingAnalysisRequest(
    @JsonProperty("argument")
    val argument: Argument
)

data class Argument(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("analysis_code")
    val analysisCode: String = "morp"
)