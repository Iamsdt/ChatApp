/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:59 PM.
 */

package com.iamsdt.chatappsendbird

import android.app.Activity
import android.os.Bundle
import com.iamsdt.chatappsendbird.di.DaggerMyComponent
import com.iamsdt.chatappsendbird.di.MyComponent
import com.iamsdt.chatappsendbird.utils.ext.LifeCycle
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApp : DaggerApplication() {

    private val component: MyComponent by lazy {
        DaggerMyComponent.builder().application(this).build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = component

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        registerActivityLifecycleCallbacks(object : LifeCycle(), HasActivityInjector {

            @Inject
            lateinit var dInjector: DispatchingAndroidInjector<Activity>

            override fun activityInjector(): AndroidInjector<Activity> = dInjector

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)
                activity?.let {
                    dInjector.maybeInject(it)
                }
            }
        })
    }
}