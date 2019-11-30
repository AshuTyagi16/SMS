package com.sasuke.sms.di.component

import com.sasuke.sms.di.module.SMSListActivityModule
import com.sasuke.sms.di.module.ViewModelFactoryModule
import com.sasuke.sms.di.module.ViewModelModule
import com.sasuke.sms.di.scope.PerActivityScope
import com.sasuke.sms.ui.sms.SMSListActivity
import dagger.Component

@PerActivityScope
@Component(
    modules = [ViewModelFactoryModule::class, ViewModelModule::class, SMSListActivityModule::class],
    dependencies = [SMSAppComponent::class]
)
interface SMSListActivityComponent {
    fun injectSMSListActivity(smsListActivity: SMSListActivity)
}