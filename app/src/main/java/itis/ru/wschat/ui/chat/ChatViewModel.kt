package itis.ru.wschat.ui.chat

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import itis.ru.wschat.api.SocketManager
import itis.ru.wschat.models.Message
import itis.ru.wschat.models.Response

class ChatViewModel(context: Activity) : ViewModel() {
    private val socketManager: SocketManager = SocketManager(context) { message, error ->
        context.runOnUiThread {
            if (message != null) {
                messageSendLiveData.value = Response.success(message)
                getMessages()
            } else if (error != null) {
                messageSendLiveData.value = Response.error(error)
            }
        }
    }

    val messagesLiveData = MutableLiveData<MutableList<Message>>()
    val messageSendLiveData = MutableLiveData<Response<String>>()

    init {
        socketManager.initSocketManager()
    }

    fun getMessages() {
        socketManager.getMessages()
    }

    fun sendMessage(message: String) {
        socketManager.sendMessage(message)
    }
}