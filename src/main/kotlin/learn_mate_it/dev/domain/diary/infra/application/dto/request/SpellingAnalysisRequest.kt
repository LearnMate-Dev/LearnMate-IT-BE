package learn_mate_it.dev.domain.diary.infra.application.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SpellingAnalysisRequest(
    @JsonProperty("document")
    val document: Document,
    @JsonProperty("encoding_type")
    val encodingType: String = "UTF16",
    @JsonProperty("auto_split_sentence")
    val autoSplitSentence: Boolean = true,
    @JsonProperty("custom_domain")
    val customDomain: String? = null
)

data class Document(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("language")
    val language: String = "ko-KR"
)