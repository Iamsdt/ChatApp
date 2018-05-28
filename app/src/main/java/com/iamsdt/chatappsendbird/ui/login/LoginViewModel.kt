package com.iamsdt.chatappsendbird.ui.login

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.iamsdt.chatappsendbird.BuildConfig
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventConfirmEmailSend
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventLoginError
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventLoginSuccessful
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventSignupError
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventSignupSuccessful
import com.iamsdt.chatappsendbird.utils.SpUtils
import com.iamsdt.chatappsendbird.utils.model.EventMessage
import com.mobapphome.mahencryptorlib.MAHEncryptor
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        val auth: FirebaseAuth,
        val bus: EventBus,
        val spUtils: SpUtils) : ViewModel() {

    private val mahEncryptor = MAHEncryptor.newInstance(BuildConfig.PsswordApiKey)

    fun loginWithPassword(email: CharSequence, pass: CharSequence) {
        auth.signInWithEmailAndPassword(email.toString(), pass.toString())
                .addOnCompleteListener({
                    if (it.isSuccessful) {
                        bus.post(EventMessage(LoginFragment.Tag, eventLoginSuccessful, 1))
                    } else {
                        bus.post(EventMessage(LoginFragment.Tag, eventLoginError, 0))
                    }
                })
    }


    fun googleLogin() {
        TODO()
    }

    fun facebookLogin() {
        TODO()
    }

    fun twitterLogin() {
        TODO()
    }

    fun signup(email: CharSequence, pass: CharSequence) {
        auth.createUserWithEmailAndPassword(email.toString(), pass.toString())
                .addOnCompleteListener({
                    if (it.isSuccessful) {

                        val newEmail = mahEncryptor.encode(email.toString())
                        val newPass = mahEncryptor.encode(email.toString())
                        spUtils.saveLogin(newEmail, newPass)
                        bus.post(EventMessage(SignupFragment.Tag, eventSignupSuccessful, 1))


                        //send email verification
                        val user = auth.currentUser
                        user?.sendEmailVerification()?.addOnCompleteListener({
                            if (it.isSuccessful) {
                                bus.post(EventMessage(SignupFragment.Tag, eventConfirmEmailSend, 1))
                            }
                        })
                    } else {
                        bus.post(EventMessage(SignupFragment.Tag, eventSignupError, 0))
                    }
                })

    }

    fun forgetPass(email: CharSequence) {
        auth.sendPasswordResetEmail(email.toString())
                .addOnCompleteListener({
                    if (it.isSuccessful) {
                        bus.post(EventMessage(LoginFragment.Tag, eventConfirmEmailSend, 1))
                    } else {
                        bus.post(EventMessage(LoginFragment.Tag, eventConfirmEmailSend, 0))
                    }
                })
    }

    fun login() {
        val user = spUtils.getUser().toList()
        val email = mahEncryptor.decode(user[0])
        val key = mahEncryptor.decode(user[1])
        loginWithPassword(email, key)
    }
}
