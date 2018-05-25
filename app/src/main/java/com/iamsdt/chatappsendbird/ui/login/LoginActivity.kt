package com.iamsdt.chatappsendbird.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.chatappsendbird.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow()
        }
    }

}
