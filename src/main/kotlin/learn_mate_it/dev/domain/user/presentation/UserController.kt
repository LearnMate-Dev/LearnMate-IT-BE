package learn_mate_it.dev.domain.user.presentation

import ApiResponse
import jakarta.servlet.http.HttpServletResponse
import learn_mate_it.dev.common.status.SuccessStatus
import learn_mate_it.dev.domain.user.application.dto.response.UserProfileDto
import learn_mate_it.dev.domain.user.application.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/logout")
    fun logout(
        @RequestHeader("Authorization") refreshToken: String,
        response: HttpServletResponse
    ): ResponseEntity<ApiResponse<Nothing>> {
        userService.logout(refreshToken)
        return ApiResponse.success(SuccessStatus.USER_LOGOUT_SUCCESS)
    }

    @DeleteMapping
    fun withDraw(
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        userService.withDraw(userId)
        return ApiResponse.success(SuccessStatus.DELETE_USER_SUCCESS)
    }

    @GetMapping("/{userId}")
    fun getUserProgile(
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<UserProfileDto>> {
        val response = userService.getUserProfile(userId)
        return ApiResponse.success(SuccessStatus.GET_USER_PROFILE_SUCCESS, response)
    }

}