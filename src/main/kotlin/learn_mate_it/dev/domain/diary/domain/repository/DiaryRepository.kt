package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.Diary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface DiaryRepository : JpaRepository<Diary, Long> {

    @Query("SELECT COUNT(d) > 0 " +
            "FROM Diary d " +
            "WHERE d.userId = :userId " +
            "AND d.createdAt >= :startDay " +
            "AND d.createdAt < :endDay")
    fun existsByUserIdAndCreatedAt(
        @Param("userId") userId: Long,
        @Param("startDay") startDay: LocalDateTime,
        @Param("endDay") endDay : LocalDateTime): Boolean

}