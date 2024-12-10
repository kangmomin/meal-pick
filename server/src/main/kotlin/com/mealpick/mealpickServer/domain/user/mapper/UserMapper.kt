package com.mealpick.mealpickServer.domain.user.mapper

import com.mealpick.mealpickServer.domain.user.model.User
import com.mealpick.mealpickServer.domain.user.model.UserFood
import com.mealpick.mealpickServer.domain.user.model.constant.FoodStatus
import com.mealpick.mealpickServer.domain.user.model.constant.FoodStatus.*
import com.mealpick.mealpickServer.domain.user.persistence.entity.UserEntity
import com.mealpick.mealpickServer.domain.user.persistence.entity.UserFoodEntity
import org.springframework.stereotype.Component

class UserMapper {

    companion object {
        fun toDomain(entity: UserEntity): User {
            val hateFoods: MutableList<UserFood> = ArrayList()
            val likeFoods: MutableList<UserFood> = ArrayList()
            val cantFoods: MutableList<UserFood> = ArrayList()

            // foods entity to domain
            entity.foods.forEach {
                val foodDomain = UserFood(
                    id = it.id,
                    name = it.name,
                    status = it.status
                )

                when (it.status) {
                    LIKE -> likeFoods += foodDomain
                    HATE -> hateFoods += foodDomain
                    CANT -> cantFoods += foodDomain
                }
            }

            return User(
                name =  entity.name,
                age = entity.age,
                sex =  entity.sex,
                hateFoods = hateFoods,
                likeFoods = likeFoods,
                cantFoods = cantFoods,
                password = entity.password,
                email = entity.email,
                provider = entity.provider
            )
        }
    }
}