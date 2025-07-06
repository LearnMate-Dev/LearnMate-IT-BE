package learn_mate_it.dev.common.status

import learn_mate_it.dev.common.base.BaseErrorStatus
import org.springframework.http.HttpStatus

enum class ErrorStatus (
    override val httpStatus: HttpStatus,
    override val code: String,
    override val message: String
) : BaseErrorStatus {

    /**
     * Common
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404", "요청한 자원을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "405", "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 내부 오류입니다."),

    /**
     * Auth
     */
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "400","유효하지 않은 OAuth2 제공자입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 액세스 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "401", "토큰이 만료되었습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "404", "존재하지 않는 리프레시 토큰입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "404", "존재하지 않는 유저입니다."),

    /**
     * Course
     */
    INVALID_COURSE_TYPE(HttpStatus.BAD_REQUEST, "400", "잘못된 코스 번호입니다. 1과 3 사이의 값을 입력해주세요."),
    INVALID_STEP_TYPE(HttpStatus.BAD_REQUEST, "400", "잘못된 스텝 번호입니다. 1과 3 사이의 값을 입력해주세요."),
    INVALID_STEP_ORDER(HttpStatus.BAD_REQUEST, "400", "이전 스텝을 마무리하지 않아 스텝을 진행할 수 없습니다."),
    INVALID_QUIZ_TYPE(HttpStatus.BAD_REQUEST, "400", "잘못된 퀴즈 번호입니다. 코스와 스텝 번호를 올바르게 입력해주세요."),

    ALREADY_ON_STEP(HttpStatus.BAD_REQUEST, "400", "이미 진행 중인 스텝입니다."),
    ALREADY_ON_QUIZ(HttpStatus.BAD_REQUEST, "400", "이미 풀이한 퀴즈입니다."),
    ALREADY_COMPLETED_STEP(HttpStatus.BAD_REQUEST, "400", "이미 완료한 스텝 진행 현황입니다."),

    NOT_FOUND_STEP_PROGRESS(HttpStatus.NOT_FOUND, "404", "존재하지 않는 스텝 진행 현황입니다."),

    /**
     * Chat
     */
    INVALID_CHAT_ROOM_TYPE(HttpStatus.BAD_REQUEST, "400", "잘못된 채팅방 유형입니다."),
    CHAT_CONTENT_OVER_FLOW(HttpStatus.BAD_REQUEST, "400", "채팅 글자수는 500자 이하여야 합니다."),
    CHAT_ROOM_TITLE_OVER_FLOW(HttpStatus.BAD_REQUEST, "400", "채팅방 제목은 30글자 이하여야 합니다."),
    ALREADY_ANALYSIS_CHAT_ROOM(HttpStatus.BAD_REQUEST, "400", "이미 분석을 완료한 채팅방은 사용할 수 없습니다."),

    FORBIDDEN_FOR_CHAT_ROOM(HttpStatus.FORBIDDEN, "403", "채팅방에 대한 접근 권한이 없습니다."),

    NOT_FOUND_CHAT_ROOM(HttpStatus.NOT_FOUND, "404", "존재하지 않는 채팅방입니다."),
    CHAT_AI_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "채팅 기능 처리 중 AI 서버에서 오류가 발생했습니다."),
    CHAT_AI_CONTENT_OVER_FLOW(HttpStatus.INTERNAL_SERVER_ERROR, "500", "채팅 응답 생성 중 오류가 발생했습니다."),
    CHAT_AI_ANALYSIS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "채팅 분석 처리 중 AI 서버에서 오류가 발생했습니다."),
    CHAT_AI_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "AI 결과 파싱 처리 중 오류가 발생했습니다."),

    /**
     * Diary
     */
    DIARY_CONTENT_OVER_FLOW(HttpStatus.BAD_REQUEST, "400", "일기의 본문은 500자 이하여야 합니다."),
    ALREADY_DIARY_WRITTEN(HttpStatus.BAD_REQUEST, "400", "일기는 하루에 한 개만 작성할 수 있습니다."),
    INVALID_YEAR_PARAM(HttpStatus.BAD_REQUEST, "400", "연도 파라미터 값이 올바르지 않습니다."),
    INVALID_MONTH_PARAM(HttpStatus.BAD_REQUEST, "400", "월 파라미터 값이 올바르지 않습니다. 1부터 12 사이의 값을 입력해주세요."),
    FORBIDDEN_FOR_DIARY(HttpStatus.FORBIDDEN, "403", "일기에 대한 접근 권한이 없습니다."),
    NOT_FOUND_DIARY(HttpStatus.NOT_FOUND, "404", "존재하지 않는 일기입니다."),
    NOT_FOUND_DATE_DIARY(HttpStatus.NOT_FOUND, "404", "해당 날짜에 작성한 일기가 존재하지 않습니다."),


    /**
     * Spelling
     */
    ANALYSIS_SPELLING_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "400", "일기 맞춤법 검사 중 클라이언트에서 오류가 발생했습니다."),
    ANALYSIS_SPELLING_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "일기 맞춤법 검사 중 서버에서 오류가 발생했습니다."),
    ANALYSIS_FEEDBACK_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "일기 맞춤법 피드백 분석 처리 중 AI 서버에서 오류가 발생했습니다."),
    SPELLING_FEEDBACK_OVER_FLOW(HttpStatus.INTERNAL_SERVER_ERROR, "500", "맞춤법 피드백 생성 결과가 제한 글자수를 초과했습니다.")


}