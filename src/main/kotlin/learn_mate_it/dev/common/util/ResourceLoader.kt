package learn_mate_it.dev.common.util

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ResourceLoader {
    private val resourceCache = mutableMapOf<String, String>()

    fun getResourceContent(resourcePath: String): String {
        return resourceCache.getOrPut(resourcePath) {
            try {
                val resource = ClassPathResource(resourcePath)
                String(resource.inputStream.readAllBytes(), StandardCharsets.UTF_8)
            } catch (e: Exception) {
                throw GeneralException(ErrorStatus.NOT_FOUND)
            }
        }
    }
}