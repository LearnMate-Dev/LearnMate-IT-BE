package learn_mate_it.dev.domain.diary.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes


@Entity
@Table(name = "spelling_revision")
data class SpellingRevision(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var spellingRevisionId: Long = 0,

    @Column(nullable = false, length = 100)
    var originContent: String,

    @Column(nullable = false, length = 100)
    var revisedContent: String,

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = true, columnDefinition = "text[]")
    var examples: Array<String>? = null,

    @Column(nullable = false, length = 200)
    var comment: String,

    @Column(nullable = false)
    var beginOffset: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spelling_id", nullable = false)
    var spelling: Spelling

) : BaseEntity()
