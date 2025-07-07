package learn_mate_it.dev.common.util

import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ResourceLoader(
) {

    fun getResourceContent(resourcePath: String): String {
        try {
            val resource = ClassPathResource(resourcePath)
            return String(resource.inputStream.readAllBytes(), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            throw GeneralException(ErrorStatus.NOT_FOUND)
        }
    }

}