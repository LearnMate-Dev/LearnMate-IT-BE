package learn_mate_it.dev.domain.user.domain.repository

import learn_mate_it.dev.domain.user.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>