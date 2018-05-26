/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:47 PM.
 */

package com.iamsdt.chatappsendbird.di.modlue

import android.app.Application
import com.iamsdt.chatappsendbird.utils.SpUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    @Provides
    @Singleton
    fun getSpUtils(application: Application):SpUtils
            = SpUtils(application)
}