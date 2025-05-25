package learn_mate_it.dev.domain.course.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.course.application.dto.response.QuizAnswerDto
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.application.service.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController (
    private val courseService: CourseService
){

    @PostMapping
    fun startStep(
        @RequestParam("course") courseLv : Int,
        @RequestParam("step") stepLv: Int
    ): ResponseEntity<ApiResponse<StepInitDto>> {
        val response = courseService.startStep(courseLv, stepLv)
        return ApiResponse.success(SuccessStatus.START_STEP_SUCCESS, response)
    }

    @PostMapping("/{stepProgressId}")
    fun solveQuiz(
        @PathVariable("stepProgressId") stepProgressId: Long,
        @RequestParam("quiz") quizLv: Int,
        @RequestParam("selectedIdx") selectedIdx: Int,
    ): ResponseEntity<ApiResponse<QuizAnswerDto>> {
        val response = courseService.solveQuiz(stepProgressId, quizLv, selectedIdx)
        return ApiResponse.success(SuccessStatus.SOLVE_QUIZ_SUCCESS, response)
    }

}