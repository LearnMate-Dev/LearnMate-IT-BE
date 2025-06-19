package learn_mate_it.dev.domain.course.presentation

import ApiResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.course.application.dto.response.CourseListDto
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.application.service.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController (
    private val courseService: CourseService
){

    @PostMapping
    fun startStep(
        @AuthenticationPrincipal userId: Long,
        @RequestParam("course") courseLv : Int,
        @RequestParam("step") stepLv: Int
    ): ResponseEntity<ApiResponse<StepInitDto>> {
        val response = courseService.startStep(userId, courseLv, stepLv)
        return ApiResponse.success(SuccessStatus.START_STEP_SUCCESS, response)
    }

    @PatchMapping("/{stepProgressId}")
    fun endStep(
        @AuthenticationPrincipal userId: Long,
        @PathVariable stepProgressId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        courseService.endStep(userId, stepProgressId)
        return ApiResponse.success(SuccessStatus.END_STEP_SUCCESS)
    }

    @DeleteMapping("/{stepProgressId}")
    fun deleteStep(
        @AuthenticationPrincipal userId: Long,
        @PathVariable stepProgressId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        courseService.deleteStep(userId, stepProgressId)
        return ApiResponse.success(SuccessStatus.DELETE_STEP_SUCCESS)
    }

    @GetMapping()
    fun getCourses(
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<CourseListDto>> {
        val response = courseService.getCourseInfo(userId)
        return ApiResponse.success(SuccessStatus.GET_COURSE_INFO_SUCCESS, response)
    }

}