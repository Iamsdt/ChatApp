package com.iamsdt.chatappsendbird.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.ui.MainActivity
import com.iamsdt.chatappsendbird.utils.SpUtils
import dagger.android.support.AndroidSupportInjection
import dmax.dialog.SpotsDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.signup_fragment.*
import timber.log.Timber
import javax.inject.Inject

class SignupFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var spUtils: SpUtils

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

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dialog = SpotsDialog(context, R.style.progress)

        button.setOnClickListener {
            val name = name_lay.editText?.text ?: ""
            val email = email_lay.editText?.text ?: ""
            val pass = pass_lay.editText?.text ?: ""

            if (isValid(email, pass)) {
                dialog.show()
                viewModel.signup(email, pass).observe(this, Observer {
                    if (it != null) {
                        if (dialog.isShowing) {
                            dialog.dismiss()
                        }
                        if (it.status == 1) {
                            Timber.i("Account Created Successfully")
                            Toasty.success(context!!, "Account Created Successfully",
                                    Toast.LENGTH_SHORT, true).show()
                            startActivity(Intent(context, MainActivity::class.java))
                        } else{
                            Timber.i("Some thing went wrong! please try again.")
                            Toasty.error(context!!, "Some thing went wrong! please try again.",
                                    Toast.LENGTH_SHORT, true).show()
                        }
                    }
                })
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

}
