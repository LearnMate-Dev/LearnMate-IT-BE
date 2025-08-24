package learn_mate_it.dev.domain.auth.domain.repository

import learn_mate_it.dev.domain.auth.domain.model.EmailVerification
import org.springframework.data.jpa.repository.JpaRepository

interface EmailVerificationRepository: JpaRepository<EmailVerification, Long> {
    fun findByEmail(email: String): EmailVerification?
}