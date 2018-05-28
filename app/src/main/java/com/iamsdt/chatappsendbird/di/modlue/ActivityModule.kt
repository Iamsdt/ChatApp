
/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:43 PM.
 */

package com.iamsdt.chatappsendbird.di.modlue


import com.iamsdt.chatappsendbird.ui.SplashScreen
import com.iamsdt.chatappsendbird.ui.login.LoginActivity
import com.iamsdt.chatappsendbird.ui.login.LoginFragment
import com.iamsdt.chatappsendbird.ui.login.SignupFragment
import com.iamsdt.chatappsendbird.utils.ConnectivityChangeReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule{

    @ContributesAndroidInjector
    abstract fun splashScreen():SplashScreen

    @ContributesAndroidInjector
    abstract fun loginActibity():LoginActivity

    @ContributesAndroidInjector
    abstract fun loginFragment():LoginFragment

    @ContributesAndroidInjector
    abstract fun signUpFragment():SignupFragment

    @ContributesAndroidInjector
    abstract fun connection():ConnectivityChangeReceiver
}