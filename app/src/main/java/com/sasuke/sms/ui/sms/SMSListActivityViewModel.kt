package com.sasuke.sms.ui.sms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.sasuke.sms.data.ListItem
import com.sasuke.sms.data.Status
import com.sasuke.sms.util.SMSUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SMSListActivityViewModel @Inject constructor(private val smsUtil: SMSUtil) : ViewModel() {

    private lateinit var oldList: ArrayList<ListItem>
    private lateinit var smsListAdapter: SMSListAdapter

    private val _statusLiveData = MutableLiveData<Status>()
    val statusLiveData: LiveData<Status>
        get() = _statusLiveData

    fun initAdapter(adapter: SMSListAdapter) {
        smsListAdapter = adapter
    }

    fun getSMSListAndUpdateUI() {
        _statusLiveData.postValue(Status.LOADING)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                smsUtil.getConsolidatedList()
            }.let { newList ->
                if (::oldList.isInitialized) {
                    withContext(Dispatchers.IO) {
                        val diff = DiffUtil.calculateDiff(MyDiffUtil(oldList, newList))
                        smsListAdapter.setList(newList)
                        oldList = newList
                        return@withContext diff
                    }.let { diffResult ->
                        diffResult.dispatchUpdatesTo(smsListAdapter)
                        _statusLiveData.postValue(Status.SUCCESS)
                    }
                } else {
                    smsListAdapter.setList(newList)
                    smsListAdapter.notifyDataSetChanged()
                    _statusLiveData.postValue(Status.SUCCESS)
                }
            }
        }
    }

    private inner class MyDiffUtil(
        val oldList: ArrayList<ListItem>,
        val newList: ArrayList<ListItem>
    ) :
        DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].getId() == oldList[newItemPosition].getId()
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}