package learn_mate_it.dev.domain.course.presentation.dto.response

data class StepInitDto (
    val stepProgressId: Long,
    val courseLv : Int,
    val stepLv : Int,
    val stepTitle: String,
    val stepDescription: String,
    val stepSituation: String,
    val firstQuiz: String,
    val firstQuizOptions: List<String>
)