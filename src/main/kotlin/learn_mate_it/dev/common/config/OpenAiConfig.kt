package learn_mate_it.dev.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAiConfig (

    @Value("\${spring.ai.openai.api-key}")
    val apiKey: String,

    @Value("\${spring.ai.openai.chat.options.model}")
    val model: String

)