package learn_mate_it.dev.domain.auth.application.dto.request

data class AppleLoginRequest(
    val username: String?,
    val identityToken: String
)