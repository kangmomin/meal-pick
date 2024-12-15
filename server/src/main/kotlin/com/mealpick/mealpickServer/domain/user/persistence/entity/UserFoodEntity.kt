package com.mealpick.mealpickServer.domain.user.persistence.entity

import com.mealpick.mealpickServer.domain.user.model.constant.FoodStatus
import com.mealpick.mealpickServer.global.common.basic.BasicEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_food")
class UserFoodEntity (
    @Column(
        nullable = false,
        length = 20
    )
    var name: String?,

    @JoinColumn(name = "tb_user.id")
    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserEntity?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: FoodStatus
) : BasicEntity()