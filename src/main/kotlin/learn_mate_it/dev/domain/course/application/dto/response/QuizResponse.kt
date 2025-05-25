package learn_mate_it.dev.domain.course.application.dto.response

import learn_mate_it.dev.domain.course.domain.enums.QuizType

data class QuizDto(
    val quizLv: Int,
    val quizSituation: String?,
    val quiz: String,
    val quizOptions: List<String>
) {
    companion object {
        fun toQuizDto(quiz: QuizType): QuizDto {
            return QuizDto(
                quizLv = quiz.level,
                quizSituation = quiz.situation,
                quiz = quiz.quiz,
                quizOptions = quiz.options.map { it.answer }
            )
        }
    }
}

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