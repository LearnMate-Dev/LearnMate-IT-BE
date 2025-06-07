package learn_mate_it.dev.common.status

import learn_mate_it.dev.common.base.BaseSuccessStatus
import org.springframework.http.HttpStatus

enum class SuccessStatus (
    override val httpStatus: HttpStatus,
    override val code: String,
    override val message: String
) : BaseSuccessStatus {

    OK(HttpStatus.OK, "200", "요청이 성공적으로 처리되었습니다."),

    // course
    START_STEP_SUCCESS(HttpStatus.OK, "200", "스텝 시작하기가 성공적으로 완료되었습니다."),
    END_STEP_SUCCESS(HttpStatus.OK, "200", "스텝 끝내기가 성공적으로 완료되었습니다."),
    DELETE_STEP_SUCCESS(HttpStatus.OK, "200", "스텝 풀이 삭제가 성공적으로 완료되었습니다."),
    SOLVE_QUIZ_SUCCESS(HttpStatus.OK, "200", "퀴즈 풀이가 성공적으로 완료되었습니다."),
    GET_COURSE_INFO_SUCCESS(HttpStatus.OK, "200", "코스별 정보 조회가 성공적으로 완료되었습니다."),

    // chat
    START_TEXT_CHAT_SUCCESS(HttpStatus.OK, "200", "텍스트 채팅방 생성이 성공적으로 완료되었습니다."),
    CHAT_WITH_TEXT_SUCCESS(HttpStatus.OK, "200", "텍스트 채팅이 성공적으로 완료되었습니다."),
    DELETE_CHAT_ROOM_SUCCESS(HttpStatus.OK, "200", "채팅방 삭제가 성공적으로 완료되었습니다."),
    ANALYSIS_CHAT_ROOM_SUCCESS(HttpStatus.OK, "200", "채팅방 분석 및 저장이 성공적으로 완료되었습니다."),
    GET_ARCHIVED_CHAT_ROOM_LIST_SUCCESS(HttpStatus.OK, "200", "저장된 채팅방 리스트 조회가 성공적으로 완료되었습니다."),
    GET_ARCHIVED_CHAT_ROOM_SUCCESS(HttpStatus.OK, "200", "채팅방 내역 조회가 성공적으로 완료되었습니다."),

}