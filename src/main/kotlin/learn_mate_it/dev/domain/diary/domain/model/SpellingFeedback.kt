package learn_mate_it.dev.domain.diary.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "spelling_feedback")
data class SpellingFeedback(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var spellingFeedbackId: Long = 0,

    @Column(columnDefinition = "text")
    var content: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    var diary: Diary

) : BaseEntity()
