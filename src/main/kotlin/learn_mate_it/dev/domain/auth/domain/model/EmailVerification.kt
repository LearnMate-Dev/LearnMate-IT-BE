package learn_mate_it.dev.domain.auth.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import java.time.LocalDateTime

@Entity
data class EmailVerification(

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    var code: String

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val emailVerificationId: Long = 0L

    @Column(nullable = false)
    var isVerified: Boolean = false

    fun verify() {
        this.isVerified = true
    }

    fun updateCode(code: String) {
        this.code = code
    }

    fun isExpired(): Boolean {
        val expirationTime = this.createdAt!!.plusMinutes(5L)
        return LocalDateTime.now().isAfter(expirationTime)
    }

}