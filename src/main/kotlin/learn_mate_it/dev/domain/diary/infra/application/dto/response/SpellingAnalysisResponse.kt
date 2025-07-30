package learn_mate_it.dev.domain.diary.infra.application.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpellingAnalysisResponse(
    val requestId: String?,
    val result: Int,
    @JsonProperty("return_object")
    val returnObject: AnalysisObject
)

data class AnalysisObject(
    @JsonProperty("doc_id")
    val docId: String,
    val category: String,
    val sentence: List<SentenceAnalysis>
)

data class SentenceAnalysis(
    val id: Int,
    @JsonProperty("reserve_str")
    val reserveStr: String,
    val text: String, // origin sentence text
    val morp: List<Morp>, // analysis result
    @JsonProperty("morp_eval")
    val morpEval: List<MorpEval>,
    val word: List<Word>,
)

data class Morp(
    val id: Int,
    val lemma: String, // 음절
    val type: String,
    val position: Int,
    val weight: Double
)

data class MorpEval(
    val id: Int,
    val result: String,
    val target: String, // lemma
    val wordId: Int,
    val mBegin: Int,
    val mEnd: Int
)

data class Word(
    val id: Int,
    val text: String, // lemma
    val type: String,
    val begin: Int,
    val end: Int
)