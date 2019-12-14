package itis.ru.wschat.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import itis.ru.wschat.R
import itis.ru.wschat.models.User
import itis.ru.wschat.ui.chat.ChatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel = LoginViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener {
            viewModel.login(User(et_name.text.toString(), et_device_id.text.toString()))
        }
        viewModel.loginLiveData.observe(this, Observer { response ->
            if (response.error == null) {
                startActivity(Intent(this, ChatActivity::class.java))
            } else {
                Toast.makeText(this, "Error ${response.error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
