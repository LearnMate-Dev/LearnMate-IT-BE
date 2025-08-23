package learn_mate_it.dev.domain.user.domain.model

import jakarta.persistence.*
import learn_mate_it.dev.common.base.BaseEntity
import learn_mate_it.dev.domain.user.domain.enums.PROVIDER

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = true)
    val providerId: String? = null,

    @Column(nullable = true)
    val password: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: PROVIDER

) : BaseEntity()