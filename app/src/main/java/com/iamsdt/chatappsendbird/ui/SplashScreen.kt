package com.iamsdt.chatappsendbird.ui

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

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

            if (spUtils.checkLogin()) {
                alertDialog = SpotsDialog(this, R.style.progress)
                alertDialog.show()

                val (e,k) = spUtils.getUser()

                auth.signInWithEmailAndPassword(
                        mahEncryptor.decode(e),
                        mahEncryptor.decode(k)).addOnCompleteListener({

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



}
