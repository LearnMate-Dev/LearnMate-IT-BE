package learn_mate_it.dev.domain.auth.domain.repository

import learn_mate_it.dev.domain.auth.domain.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long>