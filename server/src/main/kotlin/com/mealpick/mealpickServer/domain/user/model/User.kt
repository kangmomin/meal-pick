package com.mealpick.mealpickServer.domain.user.model

import com.mealpick.mealpickServer.domain.user.model.constant.LoginProvider
import com.mealpick.mealpickServer.domain.user.model.constant.SEX
import com.mealpick.mealpickServer.global.common.basic.BasicModel
import java.util.UUID

class User (
    var name: String?,
    var age: Int?,
    var sex: SEX?,
    var password: String?,
    var email: String?,
    var provider: LoginProvider?,
    var hateFoods: List<UserFood>?,
    var likeFoods: List<UserFood>?,
    var cantFoods: List<UserFood>?,
): BasicModel()