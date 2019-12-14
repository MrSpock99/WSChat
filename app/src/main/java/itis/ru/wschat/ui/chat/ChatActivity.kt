package itis.ru.wschat.ui.chat

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import itis.ru.wschat.Const
import itis.ru.wschat.R
import itis.ru.wschat.adapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private var viewModel: ChatViewModel = ChatViewModel(this)
    private var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initRecycler()
        btn_send_message.setOnClickListener {
            viewModel.sendMessage(et_message.text.toString())
            et_message.setText("")
        }
        viewModel.getMessagesLiveData.observe(this, Observer {
            if (it.data != null){
                adapter?.updateData(it.data.toMutableList())
            }
        })
        viewModel.messageSendLiveData.observe(this, Observer {
            if (it.data != null) {
                Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error ${it.error?.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecycler() {
        val username: String = getSharedPreferences(Const.PREFERENCES, Context.MODE_PRIVATE)
            .getString(Const.USERNAME, "").toString()

        adapter = MessageAdapter(username, arrayListOf())
        adapter?.setHasStableIds(true)
        rv_messages.adapter = adapter
    }
}
