package learn_mate_it.dev.domain.course.application

import learn_mate_it.dev.domain.chat.presentation.dto.response.StepInitDto

interface CourseService {

    fun startStep(courseNum: Int, stepNum: Int): StepInitDto

}