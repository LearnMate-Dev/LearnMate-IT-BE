package learn_mate_it.dev.domain.auth.application.service

interface EmailVerificationService {

    fun sendVerificationCodeToEmail(email: String)
    fun confirmEmailVerificationCode(email: String, code: String)

}