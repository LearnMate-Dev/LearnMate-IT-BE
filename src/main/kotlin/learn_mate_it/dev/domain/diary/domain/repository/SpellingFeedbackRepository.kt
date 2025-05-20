package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.SpellingFeedback
import org.springframework.data.jpa.repository.JpaRepository

interface SpellingFeedbackRepository : JpaRepository<SpellingFeedback, Long> {
}