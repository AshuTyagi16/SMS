package com.sasuke.sms.ui.sms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sasuke.sms.data.ListItem
import com.sasuke.sms.data.Resource
import com.sasuke.sms.data.SMS
import com.sasuke.sms.util.SMSUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SMSListActivityViewModel @Inject constructor(private val smsUtil: SMSUtil) : ViewModel() {

    private val _smsListLiveData = MutableLiveData<Resource<ArrayList<ListItem>>>()
    val smsListLiveData: LiveData<Resource<ArrayList<ListItem>>>
        get() = _smsListLiveData

    fun getSMSList() {
        _smsListLiveData.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                smsUtil.getConsolidatedList()
            }.let {
                _smsListLiveData.postValue(Resource.success(it))
            }
        }
    }
}