package learn_mate_it.dev.domain.diary.presentation

import learn_mate_it.dev.domain.diary.application.DiaryService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/diary"])
class DiaryController(
    private val DiaryService: DiaryService
) {
}