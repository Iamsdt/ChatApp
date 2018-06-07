package com.iamsdt.chatappsendbird.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iamsdt.chatappsendbird.BuildConfig
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.ui.login.LoginActivity
import com.iamsdt.chatappsendbird.utils.SpUtils
import com.iamsdt.chatappsendbird.utils.ext.getThread
import com.iamsdt.chatappsendbird.utils.ext.toNextActivity
import com.mobapphome.mahencryptorlib.MAHEncryptor
import dmax.dialog.SpotsDialog
import es.dmoral.toasty.Toasty
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var spUtils: SpUtils

    lateinit var alertDialog: AlertDialog

    @Inject
    lateinit var auth:FirebaseAuth

    @Inject
    lateinit var mahEncryptor:MAHEncryptor

    var timer = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (BuildConfig.DEBUG){
            timer = 100
        }

        if (spUtils.isFirstTime){
            //show app intro
            //now login
            toNextActivity(LoginActivity::class,Intent.EXTRA_TEXT,"Signup")

            //set run
            spUtils.setAppRunFirstTime()

        } else{

            val user:FirebaseUser ?= auth.currentUser

            if (user != null){
                toNextActivity(MainActivity::class)
            } else{
                login()
            }
        }

    }


    private fun login(){

        if (spUtils.isLogin) {
            alertDialog = SpotsDialog(this, R.style.progress)
            alertDialog.show()

            val (e,k) = spUtils.getUser
            //debugOnly:6/7/2018 Debug only remove latter
            val email = mahEncryptor.decode(e)
            val pass = mahEncryptor.decode(k)


            auth.signInWithEmailAndPassword(
                    email,pass).addOnCompleteListener({

                if (::alertDialog.isInitialized &&
                        alertDialog.isShowing)
                    alertDialog.dismiss()

                if (it.isSuccessful){
                    Toasty.success(this, "Login successfully",
                            Toast.LENGTH_SHORT, true).show()
                    toNextActivity(MainActivity::class)
                } else{
                    Toasty.error(this, "Some thing went wrong! Login manually.",
                            Toast.LENGTH_SHORT, true).show()
                    toNextActivity(LoginActivity::class)
                }
            })
        } else{
            val threads = getThread(timer, LoginActivity::class)
            threads.start()
        }
    }

}
