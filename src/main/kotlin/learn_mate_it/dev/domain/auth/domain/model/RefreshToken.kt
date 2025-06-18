package learn_mate_it.dev.domain.auth.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refreshToken", timeToLive = 604800000)
data class RefreshToken(
    @Id
    val refreshToken: String,
    val userId: Long
)