package learn_mate_it.dev.domain.auth.application.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AppleLoginRequest(
    val username: String?,
    val identityToken: String
)

data class SignUpRequest(
    @field:NotBlank(message = "유저 이름은 필수입니다.")
    @field:Size(max = 10, message = "유저 이름은 10자를 초과할 수 없습니다.")
    val username: String,

    @field:NotBlank(message = "이메일 주소는 필수입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @field:Size(max = 30, message = "이메일은 30자를 초과할 수 없습니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    val password: String
)

data class SignInRequest(
    @field:NotBlank(message = "이메일 주소는 필수입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @field:Size(max = 30, message = "이메일은 30자를 초과할 수 없습니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    val password: String
)

data class EmailVerificationRequest(
    @field:NotBlank(message = "이메일 주소는 필수입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @field:Size(max = 30, message = "이메일은 30자를 초과할 수 없습니다.")
    val email: String
)

data class ConfirmEmailVerificationRequest(
    @field:NotBlank(message = "이메일 주소는 필수입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @field:Size(max = 30, message = "이메일은 30자를 초과할 수 없습니다.")
    val email: String,

    @field:NotBlank(message = "인증 코드는 필수입니다.")
    val code: String
)