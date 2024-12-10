package com.mealpick.mealpickServer.global.provider

import java.io.File
import java.io.OutputStream

interface GptProvider {
    fun tts(text: String): OutputStream
    fun stt(speech: File): String
    fun chat(text: String): String
}