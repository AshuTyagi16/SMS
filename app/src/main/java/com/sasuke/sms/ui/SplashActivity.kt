package com.sasuke.sms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sasuke.sms.R
import com.sasuke.sms.ui.sms.SMSListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(SMSListActivity.newIntent(this))
        finishAffinity()
    }
}
