package com.iamsdt.chatappsendbird.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.chatappsendbird.R
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)



        signupTxt.setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SignupFragment.newInstance())
                    .commitNow()
        }
    }

}
