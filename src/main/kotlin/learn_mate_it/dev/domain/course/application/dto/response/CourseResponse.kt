package learn_mate_it.dev.domain.course.application.dto.response

import learn_mate_it.dev.domain.course.domain.enums.CourseStatus
import learn_mate_it.dev.domain.course.domain.enums.CourseType

open class CourseDto {
    var courseLv: Int = 0
    var courseDescription: String = ""
    var stepList: List<StepDto> = emptyList()
    var progress: Int = 0
    var courseStatus: CourseStatus = CourseStatus.LOCK

    companion object {
        fun toCourseDto(
            course: CourseType,
            stepList: List<StepDto>,
            progress: Int,
            courseStatus: CourseStatus
        ): CourseDto {
            val dto = CourseDto()
            dto.courseLv = course.level
            dto.courseDescription = course.description
            dto.stepList = stepList
            dto.progress = progress
            dto.courseStatus = courseStatus
            return dto
        }
    }
}

open class CourseListDto {
    var courseList: List<CourseDto> = emptyList()

    companion object {
        fun toCourseListDto(courseList: List<CourseDto>): CourseListDto {
            val dto = CourseListDto()
            dto.courseList = courseList
            return dto
        }
    }
}