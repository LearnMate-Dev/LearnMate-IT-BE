package learn_mate_it.dev.domain.auth.domain.repository

import jakarta.persistence.LockModeType
import learn_mate_it.dev.domain.auth.domain.model.EmailVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface EmailVerificationRepository: JpaRepository<EmailVerification, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByEmail(email: String): EmailVerification?
}