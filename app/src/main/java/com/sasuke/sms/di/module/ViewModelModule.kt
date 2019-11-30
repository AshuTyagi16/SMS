package com.sasuke.sms.di.module

import androidx.lifecycle.ViewModel
import com.sasuke.sms.di.mapkey.ViewModelKey
import com.sasuke.sms.ui.sms.SMSListActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SMSListActivityViewModel::class)
    abstract fun bindSMSListActivityViewModel(smsListActivityViewModel: SMSListActivityViewModel): ViewModel
}