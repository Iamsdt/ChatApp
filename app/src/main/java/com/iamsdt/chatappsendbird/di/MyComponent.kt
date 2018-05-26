package com.iamsdt.chatappsendbird.di

import android.app.Application
import com.iamsdt.chatappsendbird.di.modlue.ActivityModule
import com.iamsdt.chatappsendbird.di.modlue.AppModule
import com.iamsdt.chatappsendbird.di.modlue.EventBusModule
import com.iamsdt.chatappsendbird.di.modlue.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    EventBusModule::class,
    ViewModelModule::class])
interface MyComponent:AndroidInjector<DaggerApplication>{

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MyComponent
    }

    override fun inject(instance: DaggerApplication)

}