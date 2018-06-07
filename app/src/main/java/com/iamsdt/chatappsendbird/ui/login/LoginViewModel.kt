package com.iamsdt.chatappsendbird.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iamsdt.chatappsendbird.utils.ConstantUtils
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventConfirmEmailSend
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventSignupError
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventSignupSuccessful
import com.iamsdt.chatappsendbird.utils.SpUtils
import com.iamsdt.chatappsendbird.utils.model.EventMessage
import com.mobapphome.mahencryptorlib.MAHEncryptor
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        val auth: FirebaseAuth,
        val bus: EventBus,
        val spUtils: SpUtils,
        val mahEncryptor: MAHEncryptor) : ViewModel() {

    fun loginWithPassword(email: CharSequence, pass: CharSequence)
            : LiveData<EventMessage> {

        val mutableLiveData = MutableLiveData<EventMessage>()

        async {
            auth.signInWithEmailAndPassword(email.toString(), pass.toString())
                    .addOnCompleteListener({
                        if (it.isSuccessful) {
                            Timber.i("login successful")
                            bus.post(EventMessage(LoginFragment.Tag, ConstantUtils.eventLoginSuccessful, 1))
                            mutableLiveData.postValue(EventMessage(status = 1))
                        } else {
                            Timber.d(it.exception, "Login error")
                            bus.post(EventMessage(LoginFragment.Tag, ConstantUtils.eventLoginError, 0))
                            mutableLiveData.postValue(EventMessage(status = 0))
                        }
                    })
        }

        return mutableLiveData
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

    fun signup(email: CharSequence, pass: CharSequence): LiveData<EventMessage> {

        val mutableLiveData = MutableLiveData<EventMessage>()

        async {
            auth.createUserWithEmailAndPassword(email.toString(), pass.toString())
                    .addOnCompleteListener({
                        if (it.isSuccessful) {
                            Timber.i("Sign up successful")
                            val newEmail = mahEncryptor.encode(email.toString())
                            val newPass = mahEncryptor.encode(email.toString())
                            spUtils.saveLogin(newEmail, newPass)

                            Timber.i("Email $newEmail")
                            Timber.i("pass $newPass")

                            bus.post(EventMessage(SignupFragment.Tag, eventSignupSuccessful, 1))

                            //send email verification
                            val user:FirebaseUser = auth.currentUser!! //no chance to null
                            spUtils.saveUserID(user.uid)
                            user.sendEmailVerification().addOnCompleteListener({
                                if (it.isSuccessful) {
                                    Timber.i("Verification email send")
                                    bus.post(EventMessage(SignupFragment.Tag, eventConfirmEmailSend, 1))
                                } else
                                    Timber.d(it.exception, "Verification email sending failed")
                            })

                            mutableLiveData.postValue(EventMessage(
                                    SignupFragment.Tag,
                                    "Signup complete",
                                    1))
                        } else {
                            Timber.d(it.exception, "sign up failed")
                            bus.post(EventMessage(SignupFragment.Tag, eventSignupError, 0))
                            mutableLiveData.postValue(EventMessage(
                                    SignupFragment.Tag,
                                    "Signup failed",
                                    0))
                        }
                    })
        }

        return mutableLiveData
    }

    fun forgetPass(email: CharSequence): LiveData<EventMessage> {

        val data = MutableLiveData<EventMessage>()

        async {
            auth.sendPasswordResetEmail(email.toString())
                    .addOnCompleteListener({
                        if (it.isSuccessful) {
                            data.postValue(EventMessage(status = 1))
                        } else {
                            data.postValue(EventMessage(status = 0))
                        }
                    })
        }

        return data
    }

    fun login() {
        Timber.i("login requested")
        val (email,key) = spUtils.getUser
        //debugOnly:6/7/2018 Debug only remove latter
        Timber.i("Key and pass:${mahEncryptor.decode(email)}," +
                " ${mahEncryptor.decode(key)}")
        loginWithPassword(mahEncryptor.decode(email), mahEncryptor.decode(key))
    }
}
