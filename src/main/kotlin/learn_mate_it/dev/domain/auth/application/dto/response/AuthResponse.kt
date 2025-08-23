package learn_mate_it.dev.domain.auth.application.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)