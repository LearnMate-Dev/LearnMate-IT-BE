package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.SpellingRevision
import org.springframework.data.jpa.repository.JpaRepository

interface SpellingRevisionRepository : JpaRepository<SpellingRevision, Long> {
}