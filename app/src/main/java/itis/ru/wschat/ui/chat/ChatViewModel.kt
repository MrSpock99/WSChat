package itis.ru.wschat.ui.chat

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import itis.ru.wschat.api.SocketManager
import itis.ru.wschat.models.GetMessagesResponse
import itis.ru.wschat.models.Message
import itis.ru.wschat.models.Response


class ChatViewModel(context: Activity) : ViewModel() {
    private val socketManager: SocketManager = SocketManager(context, { message, error ->
        context.runOnUiThread {
            if (message != null) {
                messageSendLiveData.value = Response.success(message)
                getMessages(1000)
            } else if (error != null) {
                messageSendLiveData.value = Response.error(error)
            }
        }
    }, { messages, error ->
        context.runOnUiThread {
            if (messages != null) {
                val response: GetMessagesResponse =
                    Gson().fromJson(messages, GetMessagesResponse::class.java)
                getMessagesLiveData.value = Response.success(response.items)
            }
        }
    })

    val messageSendLiveData = MutableLiveData<Response<String>>()
    val getMessagesLiveData = MutableLiveData<Response<List<Message>>>()

    init {
        socketManager.initSocketManager()
    }

    fun getMessages(count: Int) {
        socketManager.getMessages(count)
    }

    fun sendMessage(message: String) {
        socketManager.sendMessage(message)
    }
}