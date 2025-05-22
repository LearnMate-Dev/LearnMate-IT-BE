package learn_mate_it.dev.domain.course.application.service

import learn_mate_it.dev.domain.course.application.dto.response.QuizAnswerDto
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto

interface CourseService {

    fun startStep(courseLv: Int, stepLv: Int): StepInitDto
    fun solveQuiz(stepProgressId: Long, quizLv: Int, selectedIdx: Int): QuizAnswerDto

}