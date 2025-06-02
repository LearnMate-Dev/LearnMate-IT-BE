package learn_mate_it.dev.domain.course.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class CourseType(
    val level: Int,
    val title: String,
    val description: String
) {

    COURSE_1(1, "한국어 훈련 1단계", ""),
    COURSE_2(2, "한국어 훈련 2단계", ""),
    COURSE_3(3, "한국어 훈련 3단계", "");

    companion object {
        fun from(level: Int): CourseType {
            return entries.find { it.level == level }
            ?: throw GeneralException(ErrorStatus.INVALID_COURSE_TYPE)
        }
    }
}