package learn_mate_it.dev.common.base

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(nullable = false)
    private var updatedAt: LocalDateTime? = null

    fun getCreatedAtFormatted(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return createdAt!!.format(formatter)
    }
}