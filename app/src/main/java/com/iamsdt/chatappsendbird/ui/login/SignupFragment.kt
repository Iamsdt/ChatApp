package com.iamsdt.chatappsendbird.ui.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.ui.MainActivity
import com.iamsdt.chatappsendbird.utils.ConstantUtils
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventConfirmEmailSend
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.eventSignupSuccessful
import com.iamsdt.chatappsendbird.utils.SpUtils
import com.iamsdt.chatappsendbird.utils.model.EventMessage
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.signup_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class SignupFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var dInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var bus: EventBus


    @Inject
    lateinit var spUtils: SpUtils
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dInjector

    companion object {
        fun newInstance() = SignupFragment()
        val Tag: String = SignupFragment::class.java.simpleName
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)

        button.setOnClickListener {
            val name = name_lay.editText?.text ?: ""
            val email = email_lay.editText?.text ?: ""
            val pass = pass_lay.editText?.text ?: ""

            if (isValid(email, pass)) {
                viewModel.signup(email, pass)
            } else {
                //something wrong
                if (isEmailValid(email)) {
                    pass_lay.error = "Please input password"
                } else {
                    email_lay.error = "Please input email"
                }
            }

            if (name.isNotEmpty()) {
                spUtils.saveDisplayName(name)
            }
        }

        loginTxt.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, LoginFragment.newInstance())
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
    fun onReceiveEvent(eventMessage: EventMessage) {
        if (eventMessage.key == Tag) {
            //we have multiple event in same tag
            //so use message to classify
            if (eventMessage.message == eventSignupSuccessful) {
                Toasty.success(activity!!, "Account Created Successfully",
                        Toast.LENGTH_SHORT, true).show()
                startActivity(Intent(activity, MainActivity::class.java))
            } else {
                Toasty.error(activity!!, "Some thing went wrong! please try again.",
                        Toast.LENGTH_SHORT, true).show()
            }


            if (eventMessage.message == eventConfirmEmailSend) {
                Toasty.info(activity!!,
                        "A confirmation email is send. Follow the email for further support",
                        Toast.LENGTH_SHORT, true).show()
            }
        }


        if (eventMessage.key == ConstantUtils.internet){
            if (eventMessage.status == 0){
                Toasty.warning(activity!!, "no internet",
                        Toast.LENGTH_SHORT, true).show()
            } else{
                Toasty.success(activity!!, "Connection is back",
                        Toast.LENGTH_SHORT, true).show()
            }
        }
    }
}
