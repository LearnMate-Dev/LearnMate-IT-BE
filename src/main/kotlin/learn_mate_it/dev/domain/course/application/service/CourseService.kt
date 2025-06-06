package learn_mate_it.dev.domain.course.application.service

import learn_mate_it.dev.domain.course.application.dto.response.CourseDto
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto

interface CourseService {

    fun startStep(courseLv: Int, stepLv: Int): StepInitDto
    fun endStep(stepProgressId: Long)
    fun deleteStep(stepProgressId: Long)
    fun getCourseInfo(courseLv: Int): CourseDto

}