package com.landa.kbck.ui.home.presenter

import android.content.Context
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.landa.library.base.BasePresenter
import com.landa.library.base.RetrofitHelper
import com.landa.library.net.ProgressObserver
import com.landa.library.net.SubscriberOnNextListener
import com.landa.kbck.entity.HttpResult
import com.landa.kbck.entity.UpdateVersion
import com.landa.kbck.service.ApiService
import com.landa.kbck.ui.home.view.HomeView
import java.util.*

/**
 * @author wyman
 * @date  2018/9/6
 * description :
 */
class HomePresenter(private val context: Context) : BasePresenter<HomeView>(context) {


    fun updateVersion() {

        val map = HashMap<String, Any>()
        map["os"] = "android"
        map["version"] = AppUtils.getAppVersionName()
        invoke(RetrofitHelper.create(ApiService::class.java).updateInfo(map), ProgressObserver(object : SubscriberOnNextListener<HttpResult<UpdateVersion>> {
            override fun onNext(t: HttpResult<UpdateVersion>) {

                if (t.code == 0) {

                    getView()?.showUpdateVersion(t.data)
                } else {
                    ToastUtils.showShort(t.msg)
                }

            }

        }, context))
    }

    fun getHomeInfo() {
        invoke(RetrofitHelper.create(ApiService::class.java).getHomeInfo(), ProgressObserver(object : SubscriberOnNextListener<HttpResult<Any>> {
            override fun onNext(t: HttpResult<Any>) {
//                if (t.code == 0) {
//                    getView()?.showHomeData(Gson().fromJson(t.data.toString(), HomeInfo::class.java))
//                } else {
//                    ToastUtils.showShort(t.msg)
//                }
            }

        }, context))
    }
}