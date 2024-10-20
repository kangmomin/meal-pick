package com.mealpick.mealpickServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MealpickServerApplication

fun main(args: Array<String>) {
	runApplication<MealpickServerApplication>(*args)
}
