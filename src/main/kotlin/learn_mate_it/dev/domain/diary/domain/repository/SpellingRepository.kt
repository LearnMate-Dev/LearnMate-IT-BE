package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.Spelling
import org.springframework.data.jpa.repository.JpaRepository

interface SpellingRepository : JpaRepository<Spelling, Long> {
}