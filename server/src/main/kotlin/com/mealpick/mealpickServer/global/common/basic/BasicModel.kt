package com.mealpick.mealpickServer.global.common.basic

import java.time.LocalDateTime
import java.util.UUID

abstract class BasicModel {
    var id: UUID? = null
    var createdAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
    var deletedAt: LocalDateTime? = null
}