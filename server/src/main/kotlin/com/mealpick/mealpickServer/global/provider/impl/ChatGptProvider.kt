package com.mealpick.mealpickServer.global.provider.impl

import com.mealpick.mealpickServer.global.provider.GptProvider
import org.springframework.ai.chat.ChatClient
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.stereotype.Component
import java.io.File
import java.io.OutputStream


@Component
class ChatGptProvider(
    private val chatGpt: ChatClient,
): GptProvider {
    private val whisperApiUrl = "https://api.openai.com/v1/audio/transcriptions"

    override fun tts(text: String): OutputStream {
        TODO()
    }

    override fun stt(speech: File): String {

        return OpenAiAudioApi.SpeechRequest(
            "tts-1",
            "",
            OpenAiAudioApi.SpeechRequest.Voice.ALLOY,
            OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3,
            1.0F
        ).model
    }

    override fun chat(text: String): String {
        TODO("Not yet implemented")
    }
}