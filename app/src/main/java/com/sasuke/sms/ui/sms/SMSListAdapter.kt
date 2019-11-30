package com.sasuke.sms.ui.sms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sasuke.sms.R
import com.sasuke.sms.data.DataItem
import com.sasuke.sms.data.HeaderItem
import com.sasuke.sms.data.ListItem
import com.sasuke.sms.data.ListItem.Companion.TYPE_DATA
import com.sasuke.sms.data.ListItem.Companion.TYPE_HEADER
import com.squareup.picasso.Picasso

class SMSListAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var smsList: ArrayList<ListItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        lateinit var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_HEADER -> {
                val view = layoutInflater
                    .inflate(R.layout.recycler_section_header, parent, false)
                viewHolder = HeaderViewHolder(view)
            }
            TYPE_DATA -> {
                val view = layoutInflater
                    .inflate(R.layout.cell_sms_list, parent, false)
                viewHolder = SMSListViewHolder(view, picasso)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).setHeader(smsList[position] as HeaderItem)
            }
            TYPE_DATA -> {
                (holder as SMSListViewHolder).setData(smsList[position] as DataItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (::smsList.isInitialized) smsList.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (::smsList.isInitialized) smsList.get(position).getType() else super.getItemViewType(
            position
        )
    }

    fun setList(list: ArrayList<ListItem>) {
        smsList = list
    }

}