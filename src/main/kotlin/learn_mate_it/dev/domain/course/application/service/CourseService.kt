package learn_mate_it.dev.domain.course.application.service

import learn_mate_it.dev.domain.course.application.dto.response.CourseListDto
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto

interface CourseService {

    fun startStep(userId: Long, courseLv: Int, stepLv: Int): StepInitDto
    fun endStep(userId: Long, stepProgressId: Long)
    fun deleteStep(userId: Long, stepProgressId: Long)
    fun getCourseInfo(userId: Long): CourseListDto

}