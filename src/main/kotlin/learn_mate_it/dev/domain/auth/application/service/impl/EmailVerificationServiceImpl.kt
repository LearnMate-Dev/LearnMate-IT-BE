package learn_mate_it.dev.domain.auth.application.service.impl

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.service.EmailVerificationService
import learn_mate_it.dev.domain.auth.domain.model.EmailVerification
import learn_mate_it.dev.domain.auth.domain.repository.EmailVerificationRepository
import learn_mate_it.dev.domain.auth.infra.application.service.EmailSendService
import org.springframework.stereotype.Service

@Service
class EmailVerificationServiceImpl(
    private val emailSendService: EmailSendService,
    private val emailVerificationRepository: EmailVerificationRepository
): EmailVerificationService {

    /**
     * Send Verification Code To Email
     */
    @Transactional
    override fun sendVerificationCodeToEmail(email: String) {
        val code = createVerificationCode()
        val verification = emailVerificationRepository.findByEmail(email)
            ?: EmailVerification(email, code)

        verification.updateCode(code)
        emailVerificationRepository.save(verification)

        emailSendService.sendEmail(email, code)
    }

    private fun createVerificationCode() = (100000..999999).random().toString()


    /**
     * Verify Email Verification Code
     */
    @Transactional
    override fun confirmEmailVerificationCode(email: String, code: String) {
        val verification = emailVerificationRepository.findByEmail(email)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_EMAIL_TO_VERIFICATION)

        if (verification.isExpired()) {
            throw GeneralException(ErrorStatus.EXPIRED_EMAIL_VERIFICATION_CODE)
        }

        if (verification.code != code) {
            throw GeneralException(ErrorStatus.INVALID_EMAIL_VERIFICATION_CODE)
        }

        verification.isVerified
    }

}