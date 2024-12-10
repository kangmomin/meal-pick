package com.mealpick.mealpickServer.global.common.exception

class CustomException (
    val errorCode: ErrorCode
): RuntimeException() {

}