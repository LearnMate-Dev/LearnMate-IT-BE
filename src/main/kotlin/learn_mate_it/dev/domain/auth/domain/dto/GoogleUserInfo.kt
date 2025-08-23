package learn_mate_it.dev.domain.auth.domain.dto

import learn_mate_it.dev.domain.user.domain.enums.PROVIDER

class GoogleUserInfo(
    private val attributes: Map<String, Any>
): OAuth2UserInfo {

    override fun getProviderId(): String {
       return attributes["sub"].toString()
    }

    override fun getName(): String {
        return attributes["name"].toString()
    }

    override fun getEmail(): String {
        return attributes["email"].toString()
    }

    override fun getProvider(): String {
        return PROVIDER.GOOGLE.name
    }
}