package com.mealpick.mealpickServer.domain.user.persistence.entity

import com.mealpick.mealpickServer.domain.user.model.UserFood
import com.mealpick.mealpickServer.domain.user.model.constant.LoginProvider
import com.mealpick.mealpickServer.domain.user.model.constant.SEX
import com.mealpick.mealpickServer.global.common.basic.BasicEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLSelect
import java.util.*

@Entity
@SQLDelete(sql = "UPDATE tb_user u SET u.deletedAt = now() WHERE u.id = ?")
@Table(name = "tb_user")
class UserEntity (
    @Column(
        nullable = false,
        length = 50,
        updatable = true,
        unique = false,
        columnDefinition = "VARCHAR"
    )
    var name: String?,

    @Column(nullable = false)
    var age: Int?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var sex: SEX?,
    var password: String?,
    var email: String?,
    var provider: LoginProvider?,

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "user",
        orphanRemoval = true)
    var foods: ArrayList<UserFoodEntity>,
): BasicEntity()