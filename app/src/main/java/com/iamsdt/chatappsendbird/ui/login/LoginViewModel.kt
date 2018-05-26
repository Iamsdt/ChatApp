package com.iamsdt.chatappsendbird.ui.login

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventSignupError
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventSignupSuccessful
import com.iamsdt.chatappsendbird.utils.model.EventMessage
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        val auth:FirebaseAuth,
        val bus:EventBus) : ViewModel() {


    fun loginWithPassword(email: CharSequence,pass:CharSequence){
        auth.signInWithEmailAndPassword(email.toString(),pass.toString())
                .addOnCompleteListener({
                    if (it.isSuccessful){
                        bus.post(EventMessage(LoginFragment.Tag,"Login",1))
                    }
                })
    }

    fun googleLogin(){
        TODO()
    }

    fun facebookLogin(){
        TODO()
    }

    fun twitterLogin(){
        TODO()
    }

    fun signup(email: CharSequence,pass: CharSequence){
        auth.createUserWithEmailAndPassword(email.toString(),pass.toString())
                .addOnCompleteListener({
                    if (it.isSuccessful){

                        bus.post(EventMessage(SignupFragment.Tag,eventSignupSuccessful,1))
                        //send email verification
                        val user = auth.currentUser
                        user?.sendEmailVerification()?.addOnCompleteListener({
                            if (it.isSuccessful){
                                bus.post(EventMessage(SignupFragment.Tag,"Verify email send",1))
                            }
                        })
                    } else{
                        bus.post(EventMessage(SignupFragment.Tag, eventSignupError,0))
                    }
                })

    }

    fun confirmEmail(code:CharSequence){
        val user = auth.currentUser
    }
}
