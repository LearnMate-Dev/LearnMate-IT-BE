package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.Diary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
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
        @Param("endDay") endDay : LocalDateTime
    ): Boolean

    @Query("SELECT d " +
            "FROM Diary d " +
            "WHERE d.diaryId = :diaryId")
    fun findByDiaryId(
        @Param("diaryId") diaryId: Long
    ): Diary?

    @Query("SELECT d " +
            "FROM Diary d " +
                "LEFT JOIN FETCH d.spelling s " +
                "LEFT JOIN FETCH s.revisions sr " +
                "LEFT JOIN FETCH d.spellingFeedback sf " +
            "WHERE d.diaryId = :diaryId")
    fun findByDiaryIdFetchSpelling(
        @Param("diaryId") diaryId: Long
    ): Diary?

    @Query("SELECT d " +
            "FROM Diary d " +
                "LEFT JOIN FETCH d.spelling s " +
                "LEFT JOIN FETCH s.revisions sr " +
                "LEFT JOIN FETCH d.spellingFeedback sf " +
            "WHERE d.userId = :userId " +
                "AND d.createdAt >= :startDay " +
                "AND d.createdAt < :endDay")
    fun findByUserIdAndCreatedAtFetchSpelling(
        @Param("userId") userId: Long,
        @Param("startDay") startDay: LocalDateTime,
        @Param("endDay") endDay : LocalDateTime
    ): Diary?

    @Modifying
    @Query("DELETE " +
                "FROM Diary d " +
                "WHERE d.userId IN :userId")
    fun deleteByUserId(@Param(value = "userId") userId: Long)

    @Modifying
    @Query("DELETE " +
            "FROM Diary d " +
            "WHERE d.diaryId = :diaryId")
    fun deleteByDiaryId(@Param("diaryId") diaryId: Long)

}