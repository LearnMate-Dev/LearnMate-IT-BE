package learn_mate_it.dev.domain.auth.application.dto

interface OAuth2UserInfo {
    fun getProviderId(): String
    fun getName(): String
    fun getProvider(): String
}