package learn_mate_it.dev.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfig: AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()

        val coreCount = Runtime.getRuntime().availableProcessors()
        executor.corePoolSize = coreCount // 기본 스레드 수
        executor.maxPoolSize = coreCount * 2  // 최대 스레드 수
        executor.queueCapacity = 10

        executor.setThreadNamePrefix("EmailAsync-")
        executor.initialize()
        return executor
    }
}