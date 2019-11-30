package com.sasuke.sms.di.module

import android.content.Context
import com.sasuke.sms.di.scope.SMSAppScope
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class PicassoModule {

    @Provides
    @SMSAppScope
    fun getPicasso(context: Context): Picasso {
        return Picasso.Builder(context).build()
    }
}