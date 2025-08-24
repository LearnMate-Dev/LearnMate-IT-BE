package learn_mate_it.dev.domain.auth.infra.application.service

import jakarta.mail.internet.MimeMessage
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailSendService(
    private val javaMailSender: JavaMailSender,
    private val templateEngine: TemplateEngine
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun sendEmail(email: String, code: String) {
        val subject = "[LearnMate] 이메일 인증 코드"

        try {
            val mimeMessage: MimeMessage = javaMailSender.createMimeMessage()
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")
            mimeMessageHelper.setTo(email)
            mimeMessageHelper.setSubject(subject)

            val content = setHtmlBody(code)
            mimeMessageHelper.setText(content, true)

            javaMailSender.send(mimeMessage)
        } catch (e: Exception) {
            log.error("[*] Sending email Code : $e")
            throw GeneralException(ErrorStatus.SEND_VERIFICATION_CODE_EMAIL_INTERNAL_SERVER_ERROR)
        }

    }

    private fun setHtmlBody(code: String): String {
        val context = Context()
        context.setVariable("verificationCode", code)
        return templateEngine.process("mail/verificationEmail", context)
    }

}