package com.landa.library.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.landa.library.BuildConfig
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.umeng.analytics.MobclickAgent
import me.yokeyword.fragmentation.Fragmentation
import kotlin.properties.Delegates

/**
 * @author wyman
 * @date  2018/9/3
 * description : base application
 */
open class App : MultiDexApplication() {
    private var refWatcher: RefWatcher? = null

    companion object {

        private val TAG = "App"
        var context: Context by Delegates.notNull()
            private set
        lateinit var instance: Application
        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as App
            return app.refWatcher
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        refWatcher = setupLeakCanary()
        initFragmentation()
        initConfig()
        initARouter()
        initUmeng()
    }

    private fun initUmeng() {
        MobclickAgent.setDebugMode(BuildConfig.DEBUG)
        MobclickAgent.startWithConfigure(MobclickAgent.UMAnalyticsConfig(context, "5b99f812f43e487cea000286", "UMENG"))
    }

    private fun initFragmentation() {
        Fragmentation.builder().stackViewMode(Fragmentation.BUBBLE).debug(true).install()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initConfig() {
        Utils.init(this)
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG)

    }

    private fun setupLeakCanary(): RefWatcher? {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else {
            LeakCanary.install(this)
        }
    }
}