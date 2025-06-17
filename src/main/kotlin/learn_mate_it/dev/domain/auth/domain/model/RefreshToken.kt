package learn_mate_it.dev.domain.auth.domain.model


data class RefreshToken(
    val refreshToken: String,
    val userId: Long
)
