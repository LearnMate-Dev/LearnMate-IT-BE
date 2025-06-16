package learn_mate_it.dev.common.config

import learn_mate_it.dev.domain.auth.handler.OAuthLoginFailureHandler
import learn_mate_it.dev.domain.auth.handler.OAuthLoginSuccessHandler
import learn_mate_it.dev.domain.auth.jwt.JwtFilter
import learn_mate_it.dev.domain.auth.jwt.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtil: JwtUtil,
    private val oAuthLoginSuccessHandler: OAuthLoginSuccessHandler,
    private val oAuthLoginFailureHandler: OAuthLoginFailureHandler
) {

    private val allowedUrls = arrayOf("/v3/**", "/swagger-ui/**", "/")

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { it.disable() }
            .cors { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers(*allowedUrls).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it.successHandler(oAuthLoginSuccessHandler)
                    .failureHandler(oAuthLoginFailureHandler)
            }
            .addFilterBefore(JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}