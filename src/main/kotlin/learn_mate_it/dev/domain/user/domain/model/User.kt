package learn_mate_it.dev.domain.user.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0,
    val username: String
) : BaseEntity()