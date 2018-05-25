package com.iamsdt.chatappsendbird.ui.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.utils.model.EventMessage
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.login_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class LoginFragment : Fragment(),HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var factory:ViewModelProvider.Factory

    @Inject
    lateinit var bus: EventBus

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            dispatchingAndroidInjector

    companion object {
        fun newInstance() = LoginFragment()
        val Tag:String = LoginFragment::class.java.simpleName
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this,factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //inject
        AndroidSupportInjection.inject(this)

        super.onActivityCreated(savedInstanceState)

        button.setOnClickListener {
            val email = email_lay.editText?.text ?: ""
            val pass = pass_lay.editText?.text ?: ""

            if (isValid(email, pass)) {
                viewModel.loginWithPassword(email,pass)
            } else {
                //something wrong
                if (isEmailValid(email)){
                    pass_lay.error = "Please input password"
                } else{
                    email_lay.error = "Please input email"
                }
            }
        }

        googleBtn.setOnClickListener {
            Toast.makeText(context, "Google", Toast.LENGTH_SHORT).show()
            viewModel.googleLogin()
        }

        fbBtn.setOnClickListener {
            Toast.makeText(context, "Facebook", Toast.LENGTH_SHORT).show()
            viewModel.facebookLogin()
        }

        twitterBtn.setOnClickListener {
            Toast.makeText(context, "Twitter", Toast.LENGTH_SHORT).show()
            viewModel.twitterLogin()
        }

        signupTxt.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, SignupFragment.newInstance())
                    ?.commitNow()
        }
    }

    private fun isValid(email: CharSequence, pass: CharSequence) =
            isEmailValid(email) && pass.isNotEmpty()

    private fun isEmailValid(email: CharSequence) =
            (email.contains("@") && email.contains(".com"))


    override fun onStart() {
        super.onStart()
        bus.isRegistered(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        bus.unregister(this)
    }

    //handle event
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEvent(eventMessage: EventMessage){
        if (eventMessage.key == Tag){
            TODO()
        }
    }

}
