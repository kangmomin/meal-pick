package com.mealpick.mealpickServer.domain.user.model

import com.mealpick.mealpickServer.domain.user.model.constant.FoodStatus
import java.util.UUID

class UserFood (
    var id: UUID?,
    var name: String?,
    var status: FoodStatus?
)