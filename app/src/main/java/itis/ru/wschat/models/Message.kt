package itis.ru.wschat.models

import java.util.*

data class Message(
    val id: String,
    val messageText: String = "",
    val fromUid: String = "",
    val sentAt: Date? = null
)
