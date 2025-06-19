package learn_mate_it.dev.domain.auth.domain.repository

import learn_mate_it.dev.domain.auth.domain.model.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {

    fun existsRefreshTokenByRefreshToken(refreshToken: String): Boolean
    fun findByRefreshToken(refreshToken: String): RefreshToken?
    fun findByUserId(userId: Long): List<RefreshToken>

}