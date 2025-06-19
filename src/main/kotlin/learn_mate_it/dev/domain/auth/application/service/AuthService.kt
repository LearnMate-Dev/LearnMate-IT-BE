package learn_mate_it.dev.domain.auth.application.service


interface AuthService {

    fun reissueToken(refreshToken: String): String

}