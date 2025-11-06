package learn_mate_it.dev.domain.auth.application.listener

import learn_mate_it.dev.domain.auth.infra.application.service.EmailSendService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class EmailSendListener(
    val emailSendService: EmailSendService
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleEmailSendEvent(event: EmailSendEvent) {
        emailSendService.sendEmail(event.email, event.code)
    }

}