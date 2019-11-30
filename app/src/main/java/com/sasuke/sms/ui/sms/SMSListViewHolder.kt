package com.sasuke.sms.ui.sms

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sasuke.sms.data.SMS
import com.sasuke.sms.util.DateTimeUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_sms_list.view.*

class SMSListViewHolder(itemView: View, private val picasso: Picasso) :
    RecyclerView.ViewHolder(itemView) {

    fun setSMS(sms: SMS) {
        picasso.load("https://www.w3schools.com/w3images/avatar2.png").into(itemView.cvAvatar)
        itemView.tvTitle.text = sms.address
        itemView.tvContent.text = sms.message
        itemView.tvDate.text = "${Math.abs(sms.hoursAgo)} hour ago"
    }
}