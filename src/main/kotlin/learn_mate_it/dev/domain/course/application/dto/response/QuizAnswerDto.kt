package learn_mate_it.dev.domain.course.application.dto.response

import learn_mate_it.dev.domain.course.domain.enums.QuizType

data class QuizAnswerDto(
    val isStepCompleted: Boolean?,
    val isCorrect: Boolean,
    val description: String,
    val nextQuiz: QuizDto?
) {
    companion object {
        fun toQuizAnswerDto(
            isStepCompleted: Boolean?,
            isCorrect: Boolean,
            description: String,
            nextQuiz: QuizType?
        ): QuizAnswerDto {
            return QuizAnswerDto(
                isStepCompleted = isStepCompleted,
                isCorrect = isCorrect,
                description = description,
                nextQuiz = nextQuiz?.let { QuizDto.toQuizDto(it) }
            )
        }
    }
}