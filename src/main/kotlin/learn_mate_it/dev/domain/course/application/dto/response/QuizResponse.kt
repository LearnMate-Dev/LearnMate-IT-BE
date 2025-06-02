package learn_mate_it.dev.domain.course.application.dto.response

import learn_mate_it.dev.domain.course.domain.enums.QuizOption
import learn_mate_it.dev.domain.course.domain.enums.QuizType

data class QuizDto(
    val quizLv: Int,
    val quizSituation: String?,
    val quiz: String,
    val correctIdx: Int,
    val quizOptions: List<QuizOptionDto>
) {
    companion object {
        fun toQuizDto(quiz: QuizType): QuizDto {
            return QuizDto(
                quizLv = quiz.level,
                quizSituation = quiz.situation,
                quiz = quiz.quiz,
                correctIdx = quiz.correctIdx,
                quizOptions = quiz.options.map{ QuizOptionDto.toQuizOptionDto(it)}
            )
        }
    }
}

data class QuizOptionDto(
    val answer: String,
    val description: String
) {
    companion object {
        fun toQuizOptionDto(quizOption: QuizOption): QuizOptionDto {
            return QuizOptionDto(
                answer = quizOption.answer,
                description = quizOption.description
            )
        }
    }
}