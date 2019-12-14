package itis.ru.wschat.models

data class Message(
    val id: Long,
    val message: String = "",
    val user: String = ""
)
