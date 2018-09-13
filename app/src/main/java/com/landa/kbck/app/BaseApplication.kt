package com.landa.kbck.app

import cn.jpush.android.api.JPushInterface
import com.landa.library.BuildConfig
import com.landa.library.app.App

/**
 * @author wyman
 * @date  2018/9/12
 * description :
 */
class BaseApplication : App() {

    override fun onCreate() {
        super.onCreate()

        JPushInterface.setDebugMode(BuildConfig.DEBUG)
        JPushInterface.init(this)
    }
}