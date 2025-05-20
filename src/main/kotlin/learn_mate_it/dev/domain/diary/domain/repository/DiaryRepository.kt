package learn_mate_it.dev.domain.diary.domain.repository

import learn_mate_it.dev.domain.diary.domain.model.Diary
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long> {
}