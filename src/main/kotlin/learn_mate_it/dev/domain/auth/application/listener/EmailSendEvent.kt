package learn_mate_it.dev.domain.auth.application.listener

data class EmailSendEvent(
    val email: String,
    val code: String
)