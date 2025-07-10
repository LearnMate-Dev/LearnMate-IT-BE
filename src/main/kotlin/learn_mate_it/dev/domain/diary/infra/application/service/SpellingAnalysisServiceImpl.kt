package learn_mate_it.dev.domain.diary.infra.application.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.diary.application.service.SpellingAnalysisService
import learn_mate_it.dev.domain.diary.infra.application.dto.request.Document
import learn_mate_it.dev.domain.diary.infra.application.dto.request.SpellingAnalysisRequest
import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Service
class SpellingAnalysisServiceImpl(
    @Value("\${spelling.host}") private val spellingAnalysisHost: String,
    @Value("\${spelling.api-key}") private val spellingApiKey: String,
    private val webClient: WebClient
): SpellingAnalysisService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override suspend fun postAnalysisSpelling(content: String): SpellingAnalysisResponse = withContext(Dispatchers.IO) {
        val request = SpellingAnalysisRequest(document = Document(content = content))

        webClient.post()
            .uri(spellingAnalysisHost)
            .header("api-key", spellingApiKey)
            .bodyValue(request)
            .retrieve()
            .onStatus({ it.is4xxClientError }) { response ->
                response.bodyToMono(String::class.java)
                    .flatMap {
                        log.error("Client error body: {}", it)
                        Mono.error(GeneralException(ErrorStatus.ANALYSIS_SPELLING_CLIENT_ERROR))
                    }
            }
            .onStatus({ it.is5xxServerError }) { response ->
                response.bodyToMono(String::class.java)
                    .flatMap {
                        log.error("Server error body: {}", it)
                        Mono.error(GeneralException(ErrorStatus.ANALYSIS_SPELLING_SERVER_ERROR))
                    }
            }
            .bodyToMono(SpellingAnalysisResponse::class.java)
            .awaitSingle()
    }
}