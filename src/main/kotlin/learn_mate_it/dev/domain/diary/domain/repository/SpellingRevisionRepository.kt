package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SpellingRevisionRepository : JpaRepository<SpellingRevision, Long> {

    @Modifying
    @Query("DELETE " +
            "FROM SpellingRevision sr " +
            "WHERE sr.spelling.diary.userId IN :userId")
    fun deleteByUserId(@Param(value = "userId") userId: Long)

    @Modifying
    @Query("DELETE " +
            "FROM SpellingRevision sr " +
            "WHERE sr.spelling.diary.diaryId IN :diaryId")
    fun deleteByDiaryId(@Param(value = "diaryId") diaryId: Long)

}