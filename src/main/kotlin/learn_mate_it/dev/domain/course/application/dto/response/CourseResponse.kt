package learn_mate_it.dev.domain.course.application.dto.response

import learn_mate_it.dev.domain.course.domain.enums.CourseStatus
import learn_mate_it.dev.domain.course.domain.enums.CourseType

data class CourseDto(
    val courseLv: Int,
    val courseDescription: String,
    val stepList: List<StepDto>,
    val progress: Int,
    val courseStatus: CourseStatus
) {
    companion object {
        fun toCourseDto(
            course: CourseType,
            stepList: List<StepDto>,
            progress: Int,
            courseStatus: CourseStatus
        ): CourseDto {
            return CourseDto(
                courseLv = course.level,
                courseDescription = course.description,
                stepList = stepList,
                progress = progress,
                courseStatus = courseStatus
            )
        }
    }
}

data class CourseListDto(
    val courseList: List<CourseDto>
) {
    companion object {
        fun toCourseListDto(courseList: List<CourseDto>): CourseListDto {
            return CourseListDto(
                courseList = courseList
            )
        }
    }
}