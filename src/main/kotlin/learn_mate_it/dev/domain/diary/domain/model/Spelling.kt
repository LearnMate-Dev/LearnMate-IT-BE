package learn_mate_it.dev.domain.diary.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "spelling")
data class Spelling(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var spellingId: Long = 0,

    @Column(nullable = false)
    var score: Int,

    @Column(columnDefinition = "text")
    var revisedContent: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    var diary: Diary,

    @OneToMany(mappedBy = "spelling", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var revisions: MutableList<SpellingRevision>? = null

) : BaseEntity()
