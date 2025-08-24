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
    protected var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(nullable = false)
    protected var updatedAt: LocalDateTime? = null

    fun getCreatedAtFormatted(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return createdAt!!.format(formatter)
    }

    fun getCreatedAtDetailedFormatted(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
        return createdAt!!.format(formatter)
    }

    fun getCreatedAtKoreanFormatted(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
        return createdAt!!.format(formatter)
    }
}