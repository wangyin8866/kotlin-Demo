package com.landa.kbck.service

import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.N
import android.os.Environment
import android.os.IBinder
import android.support.v4.content.FileProvider
import com.landa.library.utils.PreferenceLocal
import com.landa.kbck.config.Constant
import java.io.File

/**
 * 下载服务
 * @author wyman
 */

class UpdateService : Service() {

    private var update_version_id by PreferenceLocal(Constant.KEY_VERSION_ID, 0L)

    // app本地保存名
    private val appName = "lxk.apk"

    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // 安装apk
            install(context)
            stopSelf()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val downloadUrl = intent?.getStringExtra("url")
        val title = intent?.getStringExtra("title")
        if (downloadUrl != null) {
            registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            // 开始下载
            startDownload(downloadUrl, title)
        } else {
            stopSelf()
        }
        return Service.START_REDELIVER_INTENT
    }


    fun install(context: Context) {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), appName)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= N) {
            val apkUri = FileProvider.getUriForFile(context, "com.landa.lxk.fileprovider", file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun startDownload(downUrl: String, title: String?) {


        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(downUrl))
        request.setMimeType("application/vnd.android.package-archive")
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, appName)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle(title)
        val query = DownloadManager.Query()
        query.setFilterById(update_version_id)
        val cursor = dm.query(query)

        //避免相同版本重复下载
        if (!cursor.moveToFirst()) {
            // 没有记录
            val mReference = dm.enqueue(request)
            update_version_id = mReference
        } else {
            //有记录
            install(this@UpdateService)
        }

    }

}



