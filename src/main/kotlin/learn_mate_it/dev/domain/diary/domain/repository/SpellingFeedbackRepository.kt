package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.SpellingFeedback
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SpellingFeedbackRepository : JpaRepository<SpellingFeedback, Long> {

    @Modifying
    @Query("DELETE " +
            "FROM SpellingFeedback sp " +
            "WHERE sp.diary.userId IN :userId")
    fun deleteByUserId(@Param(value = "userId") userId: Long)

}