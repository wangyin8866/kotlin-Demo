package com.landa.kbck.ui.guide

import android.support.v7.app.AlertDialog
import com.landa.library.base.BaseActivity
import com.landa.library.base.BasePresenter
import com.landa.library.base.BaseView
import com.landa.library.config.BaseNetValueConfig
import com.landa.library.utils.PreferenceData
import com.landa.library.utils.PreferenceLocal
import com.landa.kbck.BuildConfig
import com.landa.kbck.config.Constant

/**
 * @author wyman
 * @date  2018/9/4
 * description :
 */
class SplashActivity : BaseActivity<BasePresenter<BaseView>, BaseView>() {
    override fun initView() {
        selectService()
    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter(mContext)
    }

    override fun getLayoutId(): Int? {
        return null
    }

    private var isFirst: Boolean by PreferenceLocal(Constant.KEY_WELCOME, false)
    private var isLogin: Boolean by PreferenceData(Constant.KEY_IS_LOGIN, false)

    private fun selectService() {
        if (BuildConfig.DEBUG) {
            showServiceSelector()
        } else {
            init()
        }
    }

    private fun showServiceSelector() {
        val names = arrayOf("开发环境", "线上环境", "测试环境")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.run {
            setTitle("选择服务器地址？")
            setCancelable(false)
            setItems(names) { _, which ->
                BaseNetValueConfig.initUrl(which)
                init()
            }
            create()
            show()

        }
    }

    private fun init() {

        if (isFirst) {
            if (isLogin) {
                openActivity(Constant.ROUTER_ACTIVITY_MAIN)
            } else {
                openActivity(Constant.ROUTER_ACTIVITY_LOGIN)
            }
        } else {
            isFirst = true
            openActivity(Constant.ROUTER_ACTIVITY_GUIDE)
        }
        closeActivity()
    }
}