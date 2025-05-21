package learn_mate_it.dev.domain.course.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.application.service.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
        val response: StepInitDto = courseService.startStep(courseLv, stepLv)
        return ApiResponse.success(SuccessStatus.START_STEP_SUCCESS, response)
    }

}