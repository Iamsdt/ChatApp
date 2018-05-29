package com.iamsdt.chatappsendbird.ui.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.iamsdt.chatappsendbird.R
import com.iamsdt.chatappsendbird.utils.ConstantUtils
import com.iamsdt.chatappsendbird.utils.model.EventMessage
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import es.dmoral.toasty.Toasty
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var bus: EventBus

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dInjector


    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onStop() {
        super.onStop()
        bus.unregister(this)
    }

    //handle event
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEvent(eventMessage: EventMessage) {

        // TODO: 5/29/2018 reduce event bus code
        if (eventMessage.key == LoginFragment.Tag) {
            if (eventMessage.message == ConstantUtils.eventConfirmEmailSend) {
                if (eventMessage.status == 1) {
                    Toasty.success(this, "Please confirm your email",
                            Toast.LENGTH_SHORT, true).show()
                }
            }
        } else if (eventMessage.key == SignupFragment.Tag) {
            if (eventMessage.message == ConstantUtils.eventConfirmEmailSend) {
                Toasty.info(this,
                        "A confirmation email is send. Follow the email for further support",
                        Toast.LENGTH_SHORT, true).show()
            }
        }

        if (eventMessage.key == ConstantUtils.internet) {
            Timber.i("internet bus received")
            if (eventMessage.status == 0) {
                Toasty.warning(this, "no internet",
                        Toast.LENGTH_SHORT, true).show()
            } else {
                Toasty.success(this, "Connection is back",
                        Toast.LENGTH_SHORT, true).show()
            }
        }
    }

}
