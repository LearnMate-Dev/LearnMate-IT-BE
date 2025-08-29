package learn_mate_it.dev.common.log

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestLoggingFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        MDC.put("requestURL", request.requestURL.toString())
        MDC.put("requestMethod", request.method)
        MDC.put("clientIP", request.remoteAddr)

        try {
            filterChain.doFilter(request, response)
        } finally {
           MDC.clear()
        }
    }

}