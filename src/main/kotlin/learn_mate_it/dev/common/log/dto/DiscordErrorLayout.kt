package learn_mate_it.dev.common.log.dto

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.ThrowableProxyUtil
import ch.qos.logback.core.LayoutBase
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DiscordErrorLayout: LayoutBase<ILoggingEvent>() {

    override fun doLayout(event: ILoggingEvent?): String {
        val sb = StringBuilder()
        val timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(event!!.timeStamp), ZoneId.systemDefault())

        sb.append("🚨 **서버 에러 발생** 🚨\n\n")
        sb.append("**에러 정보**\n")
        sb.append("```\n")
        sb.append(event.formattedMessage)
        sb.append("\n```\n")

        sb.append("**에러 발생 시간**\n")
        sb.append("`").append(timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분 ss초"))).append("`\n\n")

        sb.append("**요청 URL**\n")
        sb.append("`[")
            .append(event.mdcPropertyMap["requestMethod"])
            .append("] ")
            .append(event.mdcPropertyMap["requestURL"] ?: "")
            .append("`\n\n")

        sb.append("**요청 클라이언트**\n")
        sb.append("`[IP]: ").append(event.mdcPropertyMap["clientIP"] ?: "").append("`\n\n")

        if (event.throwableProxy != null) {
            sb.append("**에러 스택 트레이스**\n")
            sb.append("```\n")
            sb.append(ThrowableProxyUtil.asString(event.throwableProxy))
            sb.append("\n```")
        }

        return sb.toString()
    }

}