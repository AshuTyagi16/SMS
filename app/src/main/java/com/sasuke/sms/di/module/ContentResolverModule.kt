package com.sasuke.sms.di.module

import android.content.ContentResolver
import com.sasuke.sms.di.scope.SMSAppScope
import dagger.Module
import dagger.Provides

@Module
class ContentResolverModule(private val contentResolver: ContentResolver) {

    @Provides
    @SMSAppScope
    fun getContentResolver(): ContentResolver {
        return contentResolver
    }
}