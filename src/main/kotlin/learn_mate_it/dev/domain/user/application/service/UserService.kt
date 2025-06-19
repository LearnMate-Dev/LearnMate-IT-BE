package learn_mate_it.dev.domain.user.application.service

interface UserService {

    fun logout(refreshToken: String)
    fun withDraw(userId: Long)

}