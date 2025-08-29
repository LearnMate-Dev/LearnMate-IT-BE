package learn_mate_it.dev.domain.auth.infra.application.service

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.auth.application.service.AppleClient
import learn_mate_it.dev.domain.auth.infra.application.dto.response.ApplePublicKeyResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class AppleClientImpl(
    @Value("\${apple.pub-key-uri}") private val publicKeyUri: String,
    private val webClient: WebClient
): AppleClient {

    private val log = LoggerFactory.getLogger("Logger")

    /**
     * Get Public Key From Apple Server
     */
    override fun getPublicKey(): ApplePublicKeyResponse {
        return webClient.get()
            .uri(publicKeyUri)
            .retrieve()
            .onStatus({ it.is4xxClientError }) { response ->
                response.bodyToMono(String::class.java)
                    .flatMap {
                        log.error("[*] Fail to get apple pub key: Client error body: {}", it)
                        Mono.error(GeneralException(ErrorStatus.APPLE_LOGIN_PUB_KEY_CLIENT_ERROR))
                    }
            }
            .onStatus({ it.is5xxServerError }) { response ->
                response.bodyToMono(String::class.java)
                    .flatMap {
                        log.error("[*] Fail to get apple pub key: Server error body: {}", it)
                        Mono.error(GeneralException(ErrorStatus.APPLE_LOGIN_PUB_KEY_SERVER_ERROR))
                    }
            }
            .bodyToMono(ApplePublicKeyResponse::class.java)
            .block()!!
    }

}