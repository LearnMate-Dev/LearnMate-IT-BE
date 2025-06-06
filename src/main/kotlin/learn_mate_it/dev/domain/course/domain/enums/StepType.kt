package learn_mate_it.dev.domain.course.domain.enums

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus

enum class StepStatus {
        LOCK, UNSOLVED, SOLVED
}

enum class StepType (
    val course: CourseType,
    val level: Int,
    val title: String,
    val description: String,
    val quizList: List<QuizType>
) {

    /**
     * Course 1
     */
    Step1_1(CourseType.COURSE_1, 1,
        "처음 보는 사람과 인사하기",
        "인사와 대화의 첫 걸음을 배워요",
        listOf(QuizType.Quiz1_1_1,
            QuizType.Quiz1_1_2,
            QuizType.Quiz1_1_3)
    ),

    Step1_2(CourseType.COURSE_1, 2,
        "전화로 대화하기",
        "전화 예절을 배워요",
        listOf(QuizType.Quiz1_2_1,
            QuizType.Quiz1_2_2,
            QuizType.Quiz1_2_3)
    ),

    Step1_3(CourseType.COURSE_1, 3,
        "문제 생겼을 때 말하기",
        "불편하거나 문제가 생기면, 말해도 될까요?",
        listOf(QuizType.Quiz1_3_1,
            QuizType.Quiz1_3_2,
            QuizType.Quiz1_3_3)
    ),

    /**
     * Course 2
     */
    Step2_1(CourseType.COURSE_2, 1,
        "",
        "",
        listOf(QuizType.Quiz2_1_1,
            QuizType.Quiz2_1_2,
            QuizType.Quiz2_1_3)
    ),

    Step2_2(CourseType.COURSE_2, 2,
        "",
        "",
        listOf(QuizType.Quiz2_2_1,
            QuizType.Quiz2_2_2,
            QuizType.Quiz2_2_3)
    ),

    Step2_3(CourseType.COURSE_2, 3,
        "",
        "",
        listOf(QuizType.Quiz2_3_1,
            QuizType.Quiz2_3_2,
            QuizType.Quiz2_3_3)
    ),

    /**
     * Course 3
     */
    Step3_1(CourseType.COURSE_3, 1,
        "",
        "",
        listOf(QuizType.Quiz3_1_1,
            QuizType.Quiz3_1_2,
            QuizType.Quiz3_1_3)
    ),

    Step3_2(CourseType.COURSE_3, 2,
        "",
        "",
        listOf(QuizType.Quiz3_2_1,
            QuizType.Quiz3_2_2,
            QuizType.Quiz3_2_3)
    ),

    Step3_3(CourseType.COURSE_3, 3,
        "",
        "",
        listOf(QuizType.Quiz3_3_1,
            QuizType.Quiz3_3_2,
            QuizType.Quiz3_3_3)
    );

    fun isFirstStep(): Boolean {
        return this == Step1_1
    }

    fun getPreviousStep(): List<StepType> {
        val currentIdx = this.ordinal
        return if (currentIdx > 0) {
            entries.subList(0, currentIdx)
        } else {
            emptyList()
        }
    }

    companion object {
        fun from(course: CourseType, level: Int): StepType {
            return entries.find { it.course == course && it.level == level}
            ?: throw GeneralException(ErrorStatus.INVALID_STEP_TYPE)
        }

        fun getStepList(courseLv: Int): List<StepType> {
            return entries
                .filter { it.course.level == courseLv }
                .sortedBy { it.level }
        }
    }
}