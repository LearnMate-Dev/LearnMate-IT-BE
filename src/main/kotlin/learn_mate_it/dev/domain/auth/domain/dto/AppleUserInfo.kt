package learn_mate_it.dev.domain.auth.domain.dto

import learn_mate_it.dev.domain.user.domain.enums.PROVIDER

class AppleUserInfo(
    private val attributes: Map<String, Any>
) : OAuth2UserInfo {

    override fun getProviderId(): String {
        return attributes["sub"].toString()
    }

    override fun getName(): String {
        val username = attributes["username"] as? String
        if (username != null) {
            return username
        }

        return (attributes["email"] as? String)?.substringBefore("@") ?: "Apple"
    }

    override fun getEmail(): String {
        return attributes["email"].toString()
    }

    override fun getProvider(): String {
        return PROVIDER.APPLE.name
    }
}