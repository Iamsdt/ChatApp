/*
 * Developed by Shudipto Trafder
 * at  5/29/18 6:32 PM.
 */

package com.iamsdt.chatappsendbird.ui.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.utils.SpUtils
import dagger.android.support.AndroidSupportInjection
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.reset_pass.*
import javax.inject.Inject

class ResetPasswordFragment:Fragment(){
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var spUtils: SpUtils

    companion object {
        fun newInstance() = ResetPasswordFragment()
        val Tag: String = ResetPasswordFragment::class.java.simpleName
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.reset_pass, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dialog = SpotsDialog(context, R.style.progress)

        resetBtn.setOnClickListener {
            val email = resetEtLay.editText?.text ?: ""
            // TODO: 5/29/2018 add reset password
        }
    }
}