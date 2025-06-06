package learn_mate_it.dev.domain.course.application.dto.response

import learn_mate_it.dev.domain.course.domain.enums.StepStatus
import learn_mate_it.dev.domain.course.domain.enums.StepType

data class StepInitDto (
    val stepProgressId: Long,
    val courseLv : Int,
    val stepLv : Int,
    val stepTitle: String,
    val stepDescription: String,
    val quizDto: List<QuizDto>
) {
    companion object {
        fun toStepInitDto(stepProgressId: Long,
                          courseLv: Int,
                          stepLv: Int,
                          step: StepType,
        ) : StepInitDto {
            return StepInitDto(
                stepProgressId = stepProgressId,
                courseLv = courseLv,
                stepLv = stepLv,
                stepTitle = step.title,
                stepDescription = step.description,
                quizDto = step.quizList.map { QuizDto.toQuizDto(it) }
            )
        }
    }
}

data class StepDto(
    val stepLv: Int,
    val stepTitle: String,
    val stepDescription: String,
    val stepStatus: StepStatus
) {
    companion object {
        fun toStepDto(
            step: StepType,
            stepStatus: StepStatus
        ): StepDto {
            return StepDto(
                stepLv = step.level,
                stepTitle = step.title,
                stepDescription = step.description,
                stepStatus = stepStatus
            )
        }
    }
}