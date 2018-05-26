/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:46 PM.
 */

package com.iamsdt.chatappsendbird.di.modlue

import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

@Module
class EventBusModule{
    @Provides
    @Singleton
    fun getBus():EventBus = EventBus.builder()
            .logNoSubscriberMessages(false)
            .sendNoSubscriberEvent(false)
            .build()
}