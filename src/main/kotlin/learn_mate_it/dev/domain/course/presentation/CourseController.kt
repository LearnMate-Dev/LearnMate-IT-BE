package learn_mate_it.dev.domain.course.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.chat.presentation.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.application.CourseService
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
        @RequestParam("course") courseNum : Int,
        @RequestParam("step") stepNum: Int
    ): ResponseEntity<ApiResponse<StepInitDto>> {
        val response: StepInitDto = courseService.startStep(courseNum, stepNum)
        return ApiResponse.success(SuccessStatus.START_STEP_SUCCESS, response)
    }

}