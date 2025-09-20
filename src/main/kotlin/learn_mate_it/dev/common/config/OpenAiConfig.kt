package learn_mate_it.dev.common.config

import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
@EnableConfigurationProperties(OpenAiChatProperties::class)
class OpenAiConfig(
    @Value("\${spring.ai.openai.api-key}")
    val apiKey: String,

    @Value("\${spring.ai.openai.base-url}")
    val baseUrl: String
) {

    @Bean
    @Primary
    fun openAiChatModel(
        properties: OpenAiChatProperties,
        restClientBuilder: RestClient.Builder,
        webClientBuilder: WebClient.Builder
    ): OpenAiChatModel {

        val requestFactory = SimpleClientHttpRequestFactory().apply {
            setConnectTimeout(Duration.ofSeconds(30))
            setReadTimeout(Duration.ofSeconds(120))
        }

        val configuredRestClientBuilder = restClientBuilder.requestFactory(requestFactory)
        val openAiApi = OpenAiApi(baseUrl, apiKey, configuredRestClientBuilder, webClientBuilder)
        return OpenAiChatModel(openAiApi, properties.options)
    }
}