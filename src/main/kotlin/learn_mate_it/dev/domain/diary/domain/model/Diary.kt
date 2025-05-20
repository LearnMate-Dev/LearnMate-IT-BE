package learn_mate_it.dev.domain.diary.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "diaries")
data class Diary(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var diaryId: Long = 0,

    @Column(nullable = false, length = 500)
    var content: String,

    @Column(nullable = false)
    var userId: Long,

    @OneToOne(mappedBy = "diary", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var spelling: Spelling? = null,

    @OneToOne(mappedBy = "diary", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var spellingFeedback : SpellingFeedback? = null
) : BaseEntity()
