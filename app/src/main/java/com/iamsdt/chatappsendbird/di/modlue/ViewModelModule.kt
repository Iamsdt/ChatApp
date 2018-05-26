/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:44 PM.
 */

package com.iamsdt.chatappsendbird.di.modlue


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.iamsdt.chatappsendbird.di.ViewModelKey
import com.iamsdt.chatappsendbird.ui.login.LoginViewModel
import com.iamsdt.chatappsendbird.utils.ext.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindMainVM(vm: LoginViewModel): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory)
            : ViewModelProvider.Factory
}