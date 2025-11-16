package learn_mate_it.dev.domain.course.application.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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

@JsonIgnoreProperties(ignoreUnknown = true)
open class StepDto {
    var stepLv: Int = 0
    var stepTitle: String = ""
    var stepDescription: String = ""
    var stepStatus: StepStatus = StepStatus.LOCK

    fun isSolved(): Boolean {
        return this.stepStatus == StepStatus.SOLVED
    }

    companion object {
        fun toStepDto(
            step: StepType,
            stepStatus: StepStatus
        ): StepDto {
            val dto = StepDto()
            dto.stepLv = step.level
            dto.stepTitle = step.title
            dto.stepDescription = step.description
            dto.stepStatus = stepStatus
            return dto
        }
    }
}