package com.mealpick.mealpickServer.global.common.basic

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@SQLRestriction("WHERE deletedAt != null")
@MappedSuperclass
class BasicEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null

    @Column(
        updatable = false,
        nullable = false,
    )
    var createdAt: LocalDateTime? = null
    @Column(updatable = false)
    var updatedAt: LocalDateTime? = null
    var deletedAt: LocalDateTime? = null

    @PreRemove
    fun preRemove() {
        deletedAt = getCurrentTime()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = getCurrentTime()
    }

    @PrePersist
    fun prePersistence() {
        id = UUID.randomUUID()
        updatedAt = getCurrentTime()
        createdAt = getCurrentTime()
    }

    private fun getCurrentTime(): LocalDateTime? {
        val now = LocalDateTime.now()
        val seoulTimeZone = ZoneId.of("Asia/Seoul")
        return now.atZone(seoulTimeZone).toLocalDateTime()
    }
}