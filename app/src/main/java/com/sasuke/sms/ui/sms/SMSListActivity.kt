package com.sasuke.sms.ui.sms

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sasuke.sms.R
import com.sasuke.sms.SMSApp
import com.sasuke.sms.data.Status
import com.sasuke.sms.di.component.DaggerSMSListActivityComponent
import com.sasuke.sms.di.module.SMSListActivityModule
import com.sasuke.sms.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sms_list.*
import permissions.dispatcher.*
import javax.inject.Inject

@RuntimePermissions
class SMSListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: SMSListAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    private lateinit var smsListActivityViewModel: SMSListActivityViewModel

    companion object {
        private const val PERMISSION_CODE = 9921
        fun newIntent(context: Context): Intent {
            return Intent(context, SMSListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_list)
        inject()
        initialize()
        checkForPermissions()
    }

    override fun onResume() {
        super.onResume()
        checkForPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun inject() {
        val component = DaggerSMSListActivityComponent.builder()
            .sMSAppComponent(SMSApp.get(this).smsAppComponent())
            .sMSListActivityModule(SMSListActivityModule(this))
            .build()
        component.injectSMSListActivity(this)
    }

    private fun initialize() {
        smsListActivityViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SMSListActivityViewModel::class.java)
        rvSMS.layoutManager = layoutManager
        rvSMS.adapter = adapter
    }

    private fun getData() {
        smsListActivityViewModel.getSMSList()
    }

    private fun observeLiveData() {
        smsListActivityViewModel.smsListLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    rvSMS.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    rvSMS.visibility = View.VISIBLE
                    it.data?.let {
                        adapter.submitList(it)
                    }
                }
                Status.ERROR -> {
                    rvSMS.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun checkForPermissions() {
        val permissions = listOf(
            Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS
        )
        var isAllPermissionsGranted = true
        permissions.forEach {
            if (checkCallingOrSelfPermission(it) != PackageManager.PERMISSION_GRANTED) {
                isAllPermissionsGranted = false
                return@forEach
            }
        }.let {
            if (!isAllPermissionsGranted) {
                showSMSWithPermissionCheck()
            } else {
                getData()
                observeLiveData()
            }
        }
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, PERMISSION_CODE)
    }

    @NeedsPermission(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    fun showSMS() {
        getData()
        observeLiveData()
    }

    @OnShowRationale(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    fun showRationaleForSMS(request: PermissionRequest) {
        request.proceed()
    }

    @OnPermissionDenied(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    fun onSMSDenied() {
        Toast.makeText(this, R.string.please_allow_sms_permissions, Toast.LENGTH_SHORT).show()
        showSMSWithPermissionCheck()
    }

    @OnNeverAskAgain(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    fun onSMSNeverAskAgain() {
        Toast.makeText(this, R.string.please_allow_sms_permissions, Toast.LENGTH_SHORT).show()
        openSettings()
    }
}