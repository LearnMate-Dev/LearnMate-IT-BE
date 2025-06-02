package learn_mate_it.dev.domain.course.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
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

    @PatchMapping("/{stepProgressId}")
    fun endStep(
        @PathVariable stepProgressId: Long
    ): ResponseEntity<ApiResponse<String>> {
        courseService.endStep(stepProgressId)
        return ApiResponse.success(SuccessStatus.END_STEP_SUCCESS)
    }

}