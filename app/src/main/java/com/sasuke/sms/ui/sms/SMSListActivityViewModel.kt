package com.sasuke.sms.ui.sms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sasuke.sms.data.Resource
import com.sasuke.sms.data.SMS
import com.sasuke.sms.util.SMSUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SMSListActivityViewModel @Inject constructor(private val smsUtil: SMSUtil) : ViewModel() {

    private val _smsListLiveData = MutableLiveData<Resource<List<SMS>>>()
    val smsListLiveData: LiveData<Resource<List<SMS>>>
        get() = _smsListLiveData

    fun getSMSList() {
        _smsListLiveData.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                smsUtil.getAllSmsFromProvider()
            }.let {
                _smsListLiveData.postValue(Resource.success(it))
            }
        }
    }
}