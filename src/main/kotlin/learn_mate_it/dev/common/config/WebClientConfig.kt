package learn_mate_it.dev.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder.build()
    }
}