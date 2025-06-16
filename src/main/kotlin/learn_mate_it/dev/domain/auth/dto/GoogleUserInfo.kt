package learn_mate_it.dev.domain.auth.application.dto

import learn_mate_it.dev.domain.user.domain.enums.PROVIDER

class GoogleUserInfo(
    private val attributes: Map<String, Any>
): OAuth2UserInfo {

    override fun getProviderId(): String {
       return attributes.get("sub").toString()
    }

    override fun getName(): String {
        return attributes.get("name").toString()
    }

    override fun getProvider(): String {
        return PROVIDER.GOOGLE.name
    }
}