package learn_mate_it.dev.domain.diary.application.service

import learn_mate_it.dev.domain.diary.infra.application.dto.response.SpellingAnalysisResponse

interface SpellingService {

    fun analysisSpelling(content: String): SpellingAnalysisResponse

}