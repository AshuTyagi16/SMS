package com.sasuke.sms.di.module

import android.content.Context
import com.sasuke.sms.di.scope.SMSAppScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    @SMSAppScope
    fun context(): Context {
        return context
    }

}