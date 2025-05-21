package learn_mate_it.dev.domain.course.domain.enums

enum class CourseType(
    val level: Int,
    val title: String,
) {

    COURSE_1(1, "한국어 훈련 1단계"),
    COURSE_2(2, "한국어 훈련 2단계"),
    COURSE_3(3, "한국어 훈련 3단계")

}