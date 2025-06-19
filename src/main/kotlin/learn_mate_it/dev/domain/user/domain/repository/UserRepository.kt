package learn_mate_it.dev.domain.user.domain.repository

import learn_mate_it.dev.domain.user.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {

    fun findByProviderId(providerId: String): User?
    fun findByUserId(userId: Long): User?

    @Modifying
    @Query("DELETE FROM User u " +
                "WHERE u.userId = :userId")
    fun deleteByUserId(@Param(value = "userId") userId: Long)

}