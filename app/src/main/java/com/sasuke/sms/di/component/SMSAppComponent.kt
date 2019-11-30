package com.sasuke.sms.di.component

import com.sasuke.sms.di.module.ContentResolverModule
import com.sasuke.sms.di.module.PicassoModule
import com.sasuke.sms.di.module.UtilsModule
import com.sasuke.sms.di.scope.SMSAppScope
import com.sasuke.sms.util.SMSUtil
import com.squareup.picasso.Picasso
import dagger.Component

@SMSAppScope
@Component(modules = [PicassoModule::class, ContentResolverModule::class, UtilsModule::class])
interface SMSAppComponent {
    fun getPicasso(): Picasso
    fun getSMSUtil(): SMSUtil
}