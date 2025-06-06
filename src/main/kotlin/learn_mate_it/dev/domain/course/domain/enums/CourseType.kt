package learn_mate_it.dev.domain.course.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class CourseStatus {
    UNLOCK, LOCK;

    fun IsLock(): Boolean {
        return this == LOCK
    }
}

enum class CourseType(
    val level: Int,
    val title: String,
    val description: String
) {

    COURSE_1(1, "한국어 훈련 1단계", ""),
    COURSE_2(2, "한국어 훈련 2단계", ""),
    COURSE_3(3, "한국어 훈련 3단계", "");

    fun isFirstCourse(): Boolean {
        return this == COURSE_1
    }

    fun getPreviousCourse(): List<CourseType> {
        val currentIdx = this.ordinal
        return if (currentIdx > 0) {
            entries.subList(0, currentIdx)
        } else {
            emptyList()
        }
    }

    companion object {
        fun from(level: Int): CourseType {
            return entries.find { it.level == level }
            ?: throw GeneralException(ErrorStatus.INVALID_COURSE_TYPE)
        }
    }
}