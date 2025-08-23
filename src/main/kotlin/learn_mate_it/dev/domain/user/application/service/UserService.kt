package learn_mate_it.dev.domain.user.application.service

import learn_mate_it.dev.domain.user.application.dto.response.UserProfileDto

interface UserService {

    fun withDraw(userId: Long)
    fun getUserProfile(userId: Long): UserProfileDto

}