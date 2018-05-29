/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:47 PM.
 */

package com.iamsdt.chatappsendbird.di.modlue

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.iamsdt.chatappsendbird.BuildConfig
import com.iamsdt.chatappsendbird.utils.SpUtils
import com.mobapphome.mahencryptorlib.MAHEncryptor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    @Provides
    @Singleton
    fun getSpUtils(application: Application):SpUtils
            = SpUtils(application)

    @Provides
    @Singleton
    fun getAuth():FirebaseAuth
            = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun getEncreptor() = MAHEncryptor.newInstance(BuildConfig.PsswordApiKey)
}