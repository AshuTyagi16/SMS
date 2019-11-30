package com.sasuke.sms

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sasuke.sms.di.component.DaggerSMSAppComponent
import com.sasuke.sms.di.component.SMSAppComponent
import com.sasuke.sms.di.module.ContentResolverModule
import com.sasuke.sms.di.module.ContextModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber

class SMSApp : Application() {

    private lateinit var smsAppComponent: SMSAppComponent

    companion object {
        fun get(activity: Activity): SMSApp {
            return activity.application as SMSApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        smsAppComponent = DaggerSMSAppComponent.builder()
            .contextModule(ContextModule(this))
            .contentResolverModule(ContentResolverModule(contentResolver))
            .build()

        AndroidThreeTen.init(this)

        Timber.plant(Timber.DebugTree())

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Lato.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    fun smsAppComponent(): SMSAppComponent {
        return smsAppComponent
    }
}