package learn_mate_it.dev.domain.auth.application.service

import learn_mate_it.dev.domain.auth.infra.application.dto.response.ApplePublicKeyResponse

interface AppleClient {

    fun getPublicKey(): ApplePublicKeyResponse

}