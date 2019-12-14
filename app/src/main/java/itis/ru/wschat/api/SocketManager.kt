package itis.ru.wschat.api

import android.content.Context
import android.util.Log
import itis.ru.wschat.Const
import okhttp3.*
import java.util.concurrent.TimeUnit

private const val NORMAL_CLOSURE_STATUS = 1000

class SocketManager(
    private val context: Context,
    private var messageCallback: (String?, Throwable?) -> Unit
) {
    private val client: OkHttpClient = OkHttpClient().newBuilder().build()
    private lateinit var socket: WebSocket
    private var deviceIdSent = false

    fun initSocketManager() {
        val request: Request = Request.Builder().url(Const.API_CHAT).build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("Socket", "connect")
                if (!deviceIdSent) {
                }
                val deviceId =
                    context.getSharedPreferences(Const.PREFERENCES, Context.MODE_PRIVATE)
                        .getString(Const.DEVICE_ID, "")
                val json = "{ \"device_id\":\"$deviceId\" }"
                webSocket.send(json)
                deviceIdSent = true
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                messageCallback(text, null)
                Log.d("Socket", "onMessage $text")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("Socket", "onClosing $reason")
                webSocket.close(NORMAL_CLOSURE_STATUS, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                socket = client.newWebSocket(request, this)
                messageCallback(null, t)
                Log.d("Socket", "error $t response $response")
            }

        }
        socket = client.newWebSocket(request, listener)
    }

    fun getMessages() {
        val json = "{ \"history\": 5 }"
        //socket.send(json)
    }

    fun sendMessage(message: String) {
        val json = "{ \"message\":\"$message\" }"
        socket.send(json)
    }
}
