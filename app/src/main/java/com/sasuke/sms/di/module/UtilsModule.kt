package com.sasuke.sms.di.module

import android.content.ContentResolver
import com.sasuke.sms.di.scope.SMSAppScope
import com.sasuke.sms.util.SMSUtil
import dagger.Module
import dagger.Provides

@Module(includes = [ContentResolverModule::class])
class UtilsModule {

    @Provides
    @SMSAppScope
    fun getSMSUtil(contentResolver: ContentResolver): SMSUtil {
        return SMSUtil(contentResolver)
    }
}