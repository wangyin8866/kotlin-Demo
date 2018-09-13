package com.landa.kbck.ui.login

import android.content.Context
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.landa.library.base.BasePresenter
import com.landa.library.base.RetrofitHelper
import com.landa.library.config.BaseConstant
import com.landa.library.config.BaseNetValueConfig
import com.landa.library.net.ProgressObserver
import com.landa.library.net.SubscriberOnNextListener
import com.landa.library.utils.PreferenceData
import com.landa.kbck.config.Constant
import com.landa.kbck.entity.HttpResult
import com.landa.kbck.service.ApiService
import com.landa.kbck.utils.countDown

/**
 * @author wyman
 * @date  2018/9/6
 * description :
 */
class LoginPresenter(private val context: Context) : BasePresenter<LoginView>(context) {

    private var isLogin: Boolean  by PreferenceData(Constant.KEY_IS_LOGIN, false)
    private var token: String by PreferenceData(BaseConstant.KEY_TOKEN, "")

    fun sendSms(mobile: String, tv: TextView, rest: String, tip: String) {
        val map = HashMap<String, Any>()
        map["mobile"] = mobile
        map["type"] = 1
        map["client_type"] = 2
        map["business_type"] = 2

        invoke(RetrofitHelper.create(BaseNetValueConfig.LOGIN_SERVER_URL, ApiService::class.java).sendSms(map),
                ProgressObserver(object : SubscriberOnNextListener<HttpResult<Any>> {
                    override fun onNext(t: HttpResult<Any>) {
                        if (t.code == 0) {
                            ToastUtils.showShort(tip)
                            countDown(tv, rest)
                        } else {
                            ToastUtils.showShort(t.msg)
                        }
                    }

                }, context))
    }

    fun login(mobile: String, sms: String) {
        val map = HashMap<String, Any>()
        map["mobile"] = mobile
        map["login_type"] = 1
        map["client_type"] = 2
        map["captcha"] = sms
        map["business_type"] = 2

        invoke(RetrofitHelper.create(BaseNetValueConfig.LOGIN_SERVER_URL, ApiService::class.java).login(map),
                ProgressObserver(object : SubscriberOnNextListener<HttpResult<Any>> {
                    override fun onNext(t: HttpResult<Any>) {
                        if (t.code == 0) {

                            //保存登录状态
                            isLogin = true
                            token = t.data.toString()
                            getView()?.loginSuccess()
                        } else {
                            ToastUtils.showShort(t.msg)
                        }
                    }
                }, context))


    }
}