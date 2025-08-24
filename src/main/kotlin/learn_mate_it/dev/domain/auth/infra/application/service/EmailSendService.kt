package learn_mate_it.dev.domain.auth.infra.application.service

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailSendService(
    private val javaMailSender: JavaMailSender
) {

    fun sendEmail(email: String, body: String) {
        val subject = "LearnMate 이메일 인증 코드"

        val mimeMessage: MimeMessage = javaMailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")
        mimeMessageHelper.setTo(email)
        mimeMessageHelper.setSubject(subject)
        mimeMessageHelper.setText(body)

        javaMailSender.send(mimeMessage)
    }

}