package learn_mate_it.dev.domain.course.domain.enums

data class QuizOption (
    val answer: String,
    val description: String
)

enum class QuizType (
    val quiz: String,
    val correctIdx: Int,
    val options: List<QuizOption>
) {

    /**
     * Course 1
     */

    // step 1
    Quiz1_1_1("안녕하세요. 혹시 실습생이신가요?",
        1, listOf(
        QuizOption("A. \"응. 너는 누구야?\"", "올바르지 않은 표현이에요. \"응\"과 \"너는 누구야?\"는 반말이에요.\n 처음 만난 어른에게는 존댓말을 써야 해요.\n 이런 말은 상대가 기분 나쁠 수 있어요."),
        QuizOption("B. \"네, 안녕하세요. 처음 뵙겠습니다.\"", "좋은 표현이에요! \uD83D\uDC4D\uD83C\uDFFB\n 처음 만났을 때는 정중한 인사와\n 자기소개가 필요해요."),
        QuizOption("C. \"왜요?\"", "")
    )),

    Quiz1_1_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz1_1_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 2
    Quiz1_2_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz1_2_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz1_2_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 3
    Quiz1_3_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz1_3_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz1_3_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),


    /**
     * Course 2
     */
    // step 1
    Quiz2_1_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_1_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_1_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 2
    Quiz2_2_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_2_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_2_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 3
    Quiz2_3_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_3_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_3_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    /**
     * Course 3
     */
    // step 1
    Quiz3_1_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_1_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_1_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 2
    Quiz3_2_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_2_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_2_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 3
    Quiz3_3_1("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_3_2("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_3_3("",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    ))

}