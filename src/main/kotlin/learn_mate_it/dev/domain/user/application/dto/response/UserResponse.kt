package learn_mate_it.dev.domain.user.application.dto.response

import learn_mate_it.dev.domain.user.domain.model.User

data class UserProfileDto(
    val userId: Long,
    val name: String
) {
    companion object {
        fun toUserProfileDto(user: User): UserProfileDto {
            return UserProfileDto(
                userId = user.userId,
                name = user.username
            )
        }
    }
}