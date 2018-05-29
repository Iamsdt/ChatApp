package com.iamsdt.chatappsendbird.ui.login

import android.app.AlertDialog
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
import dagger.android.support.AndroidSupportInjection
import dmax.dialog.SpotsDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.login_fragment.*
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var alertDialog: AlertDialog

    companion object {
        fun newInstance() = LoginFragment()
        val Tag: String = LoginFragment::class.java.simpleName
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        alertDialog = SpotsDialog(context, R.style.progress)

        button.setOnClickListener {
            val email = email_lay.editText?.text ?: ""
            val pass = pass_lay.editText?.text ?: ""

            if (isValid(email, pass)) {
                alertDialog.show()
                viewModel.loginWithPassword(email, pass)
                        .observe(this, Observer {
                            if (it != null) {

                                if (::alertDialog.isInitialized && alertDialog.isShowing)
                                    alertDialog.dismiss()

                                if (it.status == 1) {
                                    Timber.i("Login success full")
                                    Toasty.success(context!!, "Login successfully",
                                            Toast.LENGTH_SHORT, true).show()
                                    startActivity(Intent(context, MainActivity::class.java))
                                } else {
                                    Timber.i("Login success failed")
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

        forgetPass.setOnClickListener {

            val email = email_lay.editText?.text ?: ""

            if (isEmailValid(email)) {
                // complete: 5/29/2018 move to another layout
                if (::alertDialog.isInitialized) {
                    alertDialog.setTitle("Sending...")
                    alertDialog.show()
                }
                viewModel.forgetPass(email).observe(this, Observer {
                    if (it != null){
                        if (it.status == 1){
                            activity?.supportFragmentManager?.beginTransaction()
                                    ?.replace(R.id.container, ResetPasswordFragment.newInstance())
                                    ?.commitNow()
                        } else{
                            Toasty.error(activity!!, "Something went wrong!!",
                                    Toast.LENGTH_SHORT, true).show()
                            email_lay.error = "Double check your email"
                        }
                    }
                })
            } else {
                email_lay.error = "Please input email"
                Toasty.error(activity!!, "Please insert email",
                        Toast.LENGTH_SHORT, true).show()
            }

        }
    }

    private fun isValid(email: CharSequence, pass: CharSequence) =
            isEmailValid(email) && pass.isNotEmpty()

    private fun isEmailValid(email: CharSequence) =
            (email.contains("@") && email.contains(".com"))


}
