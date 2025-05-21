package learn_mate_it.dev.domain.course.application.dto.response

import learn_mate_it.dev.domain.course.domain.enums.QuizType
import learn_mate_it.dev.domain.course.domain.enums.StepType

data class StepInitDto (
    val stepProgressId: Long,
    val courseLv : Int,
    val stepLv : Int,
    val stepTitle: String,
    val stepDescription: String,
    val stepSituation: String,
    val firstQuiz: String,
    val firstQuizOptions: List<String>
) {
    companion object {
        fun toStepInitDto(stepProgressId: Long,
                          courseLv: Int,
                          stepLv: Int,
                          step: StepType,
                          quiz: QuizType
                          ) : StepInitDto {
            return StepInitDto(
                stepProgressId = stepProgressId,
                courseLv = courseLv,
                stepLv = stepLv,
                stepTitle = step.title,
                stepDescription = step.description,
                stepSituation = step.situation,
                firstQuiz = quiz.quiz,
                firstQuizOptions = quiz.options.map { it.answer }
            )
        }
    }
}