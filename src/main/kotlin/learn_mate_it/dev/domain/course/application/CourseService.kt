package learn_mate_it.dev.domain.course.application

import learn_mate_it.dev.domain.course.presentation.dto.response.StepInitDto

interface CourseService {

    fun startStep(courseLv: Int, stepLv: Int): StepInitDto

}