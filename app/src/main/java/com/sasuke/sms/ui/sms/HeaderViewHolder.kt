package com.sasuke.sms.ui.sms

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sasuke.sms.R
import com.sasuke.sms.data.HeaderItem
import kotlinx.android.synthetic.main.recycler_section_header.view.*

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setHeader(headerItem: HeaderItem) {
        itemView.tvHeader.text =
            String.format(itemView.context.getString(R.string.hours_ago), headerItem.hoursAgo)
    }
}