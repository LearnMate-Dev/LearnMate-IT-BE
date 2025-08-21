package learn_mate_it.dev.domain.auth.domain.dto

import learn_mate_it.dev.domain.user.domain.enums.PROVIDER

class AppleUserInfo(
    private val attributes: Map<String, Any>
) : OAuth2UserInfo {

    override fun getProviderId(): String {
        return attributes.get("sub").toString()
    }

    override fun getName(): String {
        return attributes.get("email") as? String ?: "Apple"
    }

    override fun getProvider(): String {
        return PROVIDER.APPLE.name
    }
}