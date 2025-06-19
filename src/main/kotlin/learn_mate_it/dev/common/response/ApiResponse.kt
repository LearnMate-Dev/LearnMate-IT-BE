import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import learn_mate_it.dev.common.base.BaseErrorStatus
import learn_mate_it.dev.common.base.BaseSuccessStatus
import org.springframework.http.ResponseEntity

@JsonPropertyOrder("isSuccess", "code", "message", "data")
data class ApiResponse<T>(
    @JsonProperty("is_success")
    val isSuccess: Boolean,
    val code: String,
    val message: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null
) {
    companion object {
        fun success(successStatus: BaseSuccessStatus): ResponseEntity<ApiResponse<Nothing>> {
            return ResponseEntity
                .status(successStatus.httpStatus)
                .body(ApiResponse(true, successStatus.code, successStatus.message, null))
        }

        fun <T> success(successStatus: BaseSuccessStatus, data: T): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity
                .status(successStatus.httpStatus)
                .body(ApiResponse(true, successStatus.code, successStatus.message, data))
        }

        fun error(errorStatus: BaseErrorStatus): ResponseEntity<ApiResponse<Nothing>> {
            return ResponseEntity
                .status(errorStatus.httpStatus)
                .body(ApiResponse(false, errorStatus.code, errorStatus.message, null))
        }

        fun error(errorStatus: BaseErrorStatus, message: String): ResponseEntity<ApiResponse<Nothing>> {
            return ResponseEntity
                .status(errorStatus.httpStatus)
                .body(ApiResponse(false, errorStatus.code, message, null))
        }
    }
}
