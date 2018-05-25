package com.iamsdt.chatappsendbird.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.chatappsendbird.BuildConfig
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.ui.login.LoginActivity
import com.iamsdt.chatappsendbird.utils.ext.getThread
import com.iamsdt.chatappsendbird.utils.ext.isAppRunForFirstTime
import com.iamsdt.chatappsendbird.utils.ext.setAppRunFirstTime

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var timer = 1500L

        if (BuildConfig.DEBUG){
            timer = 100
        }

        if (isAppRunForFirstTime(this)){
            //show app intro
            //now login

            val threads = getThread(timer, LoginActivity::class)
            threads.start()

            //set run
            setAppRunFirstTime(this)
        } else{
            val threads = getThread(timer, LoginActivity::class)
            threads.start()
        }

    }



}
