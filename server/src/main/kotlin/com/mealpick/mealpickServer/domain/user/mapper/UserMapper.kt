package com.mealpick.mealpickServer.domain.user.mapper

import com.mealpick.mealpickServer.domain.user.model.User
import com.mealpick.mealpickServer.domain.user.model.UserFood
import com.mealpick.mealpickServer.domain.user.model.constant.FoodStatus.*
import com.mealpick.mealpickServer.domain.user.persistence.entity.UserEntity
import com.mealpick.mealpickServer.domain.user.persistence.entity.UserFoodEntity

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

        fun toEntity(domain: User): UserEntity {
            var foods: MutableList<UserFoodEntity> = ArrayList()

            foods.addAll(domain.cantFoods?.map {
                UserFoodEntity(
                    name = it.name,
                    user = null,
                    CANT
                )
            } ?: ArrayList())
            foods.addAll(domain.hateFoods?.map {
                UserFoodEntity(
                    name = it.name,
                    user = null,
                    HATE
                )
            } ?: ArrayList())
            foods.addAll(domain.likeFoods?.map {
                UserFoodEntity(
                    name = it.name,
                    user = null,
                    LIKE
                )
            } ?: ArrayList())

            return UserEntity(
                name =  domain.name,
                age = domain.age,
                sex =  domain.sex,
                foods = foods,
                password = domain.password,
                email = domain.email,
                provider = domain.provider
            )
        }
    }
}