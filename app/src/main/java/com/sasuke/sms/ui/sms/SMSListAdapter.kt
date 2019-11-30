package com.sasuke.sms.ui.sms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sasuke.sms.R
import com.sasuke.sms.data.SMS
import com.squareup.picasso.Picasso

class SMSListAdapter(diffUtil: DiffUtil.ItemCallback<SMS>, private val picasso: Picasso) :
    ListAdapter<SMS, SMSListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_sms_list, parent, false)
        return SMSListViewHolder(view, picasso)
    }

    override fun onBindViewHolder(holder: SMSListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.setSMS(it)
        }
    }

}