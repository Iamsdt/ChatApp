package com.iamsdt.chatappsendbird.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.chatappsendbird.BuildConfig
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.ui.login.LoginActivity
import com.iamsdt.chatappsendbird.utils.SpUtils
import com.iamsdt.chatappsendbird.utils.ext.getThread
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var spUtils: SpUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timer = 1500L

        if (BuildConfig.DEBUG){
            timer = 100
        }

        if (spUtils.isAppRunForFirstTime()){
            //show app intro
            //now login

            val threads = getThread(timer, LoginActivity::class)
            threads.start()

            //set run
            spUtils.setAppRunFirstTime()
        } else{
            val threads = getThread(timer, LoginActivity::class)
            threads.start()
        }

    }



}
