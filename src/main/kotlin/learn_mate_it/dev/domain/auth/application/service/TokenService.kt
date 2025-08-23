package learn_mate_it.dev.domain.auth.application.service

interface TokenService {

    fun reissueToken(refreshToken: String): String
    fun deleteRefreshToken(userId: Long)
    fun deleteRefreshToken(refreshToken: String)
    fun createAndSaveToken(userId: Long): Pair<String, String>

}