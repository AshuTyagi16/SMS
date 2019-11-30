package com.sasuke.sms.di.module

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sasuke.sms.data.SMS
import com.sasuke.sms.di.scope.PerActivityScope
import com.sasuke.sms.ui.sms.SMSListAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides


@Module
class SMSListActivityModule(private val context: Context) {

    @Provides
    @PerActivityScope
    fun layoutManager(): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    @PerActivityScope
    fun getAdapter(diffUtil: DiffUtil.ItemCallback<SMS>, picasso: Picasso): SMSListAdapter {
        return SMSListAdapter(diffUtil, picasso)
    }

    @Provides
    @PerActivityScope
    fun getDiffUtil(): DiffUtil.ItemCallback<SMS> {
        return object : DiffUtil.ItemCallback<SMS>() {
            override fun areItemsTheSame(oldItem: SMS, newItem: SMS): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SMS, newItem: SMS): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}