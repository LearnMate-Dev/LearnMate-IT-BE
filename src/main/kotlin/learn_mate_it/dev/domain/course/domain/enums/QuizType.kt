package learn_mate_it.dev.domain.course.domain.enums


data class QuizOption (
    val answer: String,
    val description: String
)

enum class QuizType (
    val step: StepType,
    val level: Int,
    val situation: String?,
    val quiz: String,
    val correctIdx: Int,
    val options: List<QuizOption>
) {

    /**
     * Course 1
     */

    // step 1
    Quiz1_1_1(StepType.Step1_1, 1,
        "당신은 체험 첫날, 안내 데스크에 도착했어요.\n 직원 한 분이 다가오며 웃으면서 말을 걸어요.",
        "안녕하세요. 혹시 실습생이신가요?",
        1, listOf(
        QuizOption("A. \"응. 너는 누구야?\"", "올바르지 않은 표현이에요. \"응\"과 \"너는 누구야?\"는 반말이에요.\n 처음 만난 어른에게는 존댓말을 써야 해요.\n 이런 말은 상대가 기분 나쁠 수 있어요."),
        QuizOption("B. \"네, 안녕하세요. 처음 뵙겠습니다.\"", "좋은 표현이에요! \uD83D\uDC4D\uD83C\uDFFB\n 처음 만났을 때는 정중한 인사와\n 자기소개가 필요해요."),
        QuizOption("C. \"왜요?\"", "")
    )),

    Quiz1_1_2(StepType.Step1_1, 2,
        "당신은 체험 가는 길에 지하철역에서 길을 헤매고 있어요.\n 어른 한 분이 다가와서 길을 묻습니다.",
        "혹시 여기서 시청역 가려면 어디로 가야 할까요?",
        1, listOf(
        QuizOption("A. \"아 몰라요. 저도 몰라요.\"", "예의 없는 표현이에요. 정중하게 말하거나 죄송하다는 표현이 더 적절해요."),
        QuizOption("B. \"죄송해요. 저도 잘 모르겠어요.\"", "좋은 표현이에요! 정중하게 모른다고 말하는 것은 예의 있는 태도예요."),
        QuizOption("C. \"네비 찍으세요.\"", "무례하게 들릴 수 있는 말이에요. 처음 만난 어른에게는 친절하고 공손하게 말하는 게 좋아요."),
    )),

    Quiz1_1_3(StepType.Step1_1, 3,
        "체험 첫날, 잠시 안내 데스크를 맡게 되었어요.\n 전화벨이 울리고 전화를 받자 낯선 목소리가 들립니다.",
        "안녕하세요. 혹시 인사팀 연결 가능할까요?",
        2, listOf(
        QuizOption("A. \"누구세요? 왜요?\"", "무례하게 들릴 수 있어요. 처음 듣는 목소리라도 예의 있게 응대해야 해요."),
        QuizOption("B. \"저 실습생이라 몰라요.\"", "솔직하지만 무책임한 응대처럼 들릴 수 있어요. 가능하면 도와주려는 태도가 필요해요."),
        QuizOption("C. \"네, 잠시만 기다려 주세요. 인사팀 연결해 드리겠습니다.\"", "좋은 응대예요! 공손하고 명확한 표현으로 상대를 존중하고 있어요."),
    )),

    // step 2
    Quiz1_2_1(StepType.Step1_2, 1,
        "Quiz1-2-1 Situation",
        "quiz1_2_1",
        0, listOf(
        QuizOption("A. Quiz1-2-1", "Quiz1-2-1 A Description"),
        QuizOption("B. Quiz1-2-1", "Quiz1-2-1 B Description"),
        QuizOption("C. Quiz1-2-1", "Quiz1-1-2 C Description"),
    )),

    Quiz1_2_2(StepType.Step1_2, 2,
        "Quiz1-2-2 Situation",
        "quiz1_2_2",
        0, listOf(
        QuizOption("A. Quiz1-2-2", "Quiz1-2-2 A Description"),
        QuizOption("B. Quiz1-2-2", "Quiz1-2-2 B Description"),
        QuizOption("C. Quiz1-2-2", "Quiz1-2-2 C Description"),
    )),

    Quiz1_2_3(StepType.Step1_2, 3,
        "Quiz1-2-3 Situation",
        "quiz1_2_3",
        0, listOf(
        QuizOption("A. Quiz1-2-3", "Quiz1-2-3 A Description"),
        QuizOption("B. Quiz1-2-3", "Quiz1-2-3 B Description"),
        QuizOption("C. Quiz1-2-3", "Quiz1-2-3 C Description"),
    )),

    // step 3
    Quiz1_3_1(StepType.Step1_3, 1,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz1_3_2(StepType.Step1_3, 2,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz1_3_3(StepType.Step1_3, 3,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),


    /**
     * Course 2
     */
    // step 1
    Quiz2_1_1(StepType.Step2_1, 1,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_1_2(StepType.Step2_1, 2,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_1_3(StepType.Step2_1, 3,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 2
    Quiz2_2_1(StepType.Step2_2, 1,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_2_2(StepType.Step2_2, 2,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_2_3(StepType.Step2_2, 3,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 3
    Quiz2_3_1(StepType.Step2_3, 1,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_3_2(StepType.Step2_3, 2,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz2_3_3(StepType.Step2_3, 3,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    /**
     * Course 3
     */
    // step 1
    Quiz3_1_1(StepType.Step3_1, 1,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_1_2(StepType.Step3_1, 2,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_1_3(StepType.Step3_1, 3,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 2
    Quiz3_2_1(StepType.Step3_2, 1,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_2_2(StepType.Step3_2, 2,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_2_3(StepType.Step3_2, 3,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    // step 3
    Quiz3_3_1(StepType.Step3_3, 1,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_3_2(StepType.Step3_3, 2,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    )),

    Quiz3_3_3(StepType.Step3_3, 3,
        "",
        "",
        0, listOf(
        QuizOption("A. ", ""),
        QuizOption("B. ", ""),
        QuizOption("C. ", "")
    ));

}