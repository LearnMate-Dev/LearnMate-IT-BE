package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.Spelling
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SpellingRepository : JpaRepository<Spelling, Long> {

    @Modifying
    @Query("DELETE " +
            "FROM Spelling s " +
            "WHERE s.diary.userId IN :userId")
    fun deleteByUserId(@Param(value = "userId") userId: Long)

    @Modifying
    @Query(
        nativeQuery = true,
        value = """
        DELETE FROM spelling 
        WHERE diary_id = :diaryId
    """
    )
    fun deleteByDiaryId(@Param(value = "diaryId") diaryId: Long)

}