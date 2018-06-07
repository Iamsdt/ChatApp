/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:59 PM.
 */

package com.iamsdt.chatappsendbird

import android.app.Activity
import android.os.Bundle
import com.iamsdt.chatappsendbird.di.DaggerMyComponent
import com.iamsdt.chatappsendbird.di.MyComponent
import com.iamsdt.chatappsendbird.utils.ext.DebugLogTree
import com.iamsdt.chatappsendbird.utils.ext.LifeCycle
import com.iamsdt.chatappsendbird.utils.ext.ReleaseLogTree
import com.rohitss.uceh.UCEHandler
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MyApp : DaggerApplication() {

    private val component: MyComponent by lazy {
        DaggerMyComponent.builder().application(this).build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = component

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        if (BuildConfig.DEBUG) Timber.plant(DebugLogTree())
        else Timber.plant(ReleaseLogTree())


        //SendBird.init(BuildConfig.api,this)

        UCEHandler.Builder(applicationContext).build()

        registerActivityLifecycleCallbacks(object : LifeCycle() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)
                activity?.let {
                    //is their any option to check
                    // is ContributesAndroidInjector available for this activity first
                    try {
                        AndroidInjection.inject(it)
                    }catch (e:Exception){
                        //Timber.d(e,"Inject error")
                    }
                }
            }
        })
    }
}